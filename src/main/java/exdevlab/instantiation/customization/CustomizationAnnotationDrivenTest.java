package exdevlab.instantiation.customization;

import exdevlab.instantiation.IncorrectInheritanceException;
import exdevlab.instantiation.core.KernelDrivenTest;

import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class CustomizationAnnotationDrivenTest extends KernelDrivenTest implements ICustomizationAttributeDrivenTest
{
    private List<Class> _invokedAttributes;
    private List<Class> _hiddenAttributes;

    protected CustomizationAnnotationDrivenTest()
    {
        _hiddenAttributes = new ArrayList<>();
        _invokedAttributes = new ArrayList<>();
//        Kernel.Bind<CustomizationAttributeDrivenTest>().ToConstant(this);
    }

    public void ApplyCustomization() throws IllegalAccessException, InstantiationException, IncorrectInheritanceException {
//        var frame = new StackFrame(1);
//        var callingMethod = frame.GetMethod();
//        var targetType = callingMethod.DeclaringType ?? GetType();
//        List<Class> annoList = new List<CustomizationAttribute>(GetType().GetCustomAttributes<CustomizationAttribute>()
//        .Where(a => a.GetType().GetInterfaces().Where(i => i.IsGenericType).Any(i => i.GetGenericTypeDefinition() == typeof(ICustomizationAttribute<>)))
//        .Where(a => a.GetCustomizationTargetType() == targetType || targetType.IsSubclassOf(a.GetCustomizationTargetType())));


        Customize[] customizations = getClass().getAnnotationsByType(Customize.class);
        List<Customizer> customizers = new ArrayList<>();
        customizers.forEach();
        for (Customize customization: customizations) {
            Class customizer = customization.value();
            if(Customizer.class.isAssignableFrom(customizer)) {
                Customizer instance = (Customizer)customizer.newInstance();
                customizers.add(instance);
                continue;
            }
            throw new IncorrectInheritanceException(new String[] {customizer.getTypeName()}, new String[]{Customizer.class.getTypeName()});
        }
        customizers.sort((c1, c2) ->
        {
            Class mineTargetType = c1.GetCustomizationTargetType();
            Class othersTargetType = c2.GetCustomizationTargetType();
            if (mineTargetType.isAssignableFrom(othersTargetType) && mineTargetType != othersTargetType) return 1;
            if (othersTargetType.isAssignableFrom(mineTargetType) && mineTargetType != othersTargetType) return -1;
            return c1.Priority == 0 ? 1 : c2.Priority == 0 ? -1 : c1.Priority - c2.Priority;
        });

        customizers = ApplyTheOnlyPolicy(customizers);
        attributeList.ForEach(a =>
        {
            if (_invokedAttributes.Any(i => i == a.GetType()) ||
            _hiddenAttributes.Any(i => i == a.GetType())) return;
            if (a.HasToBeCanceled(_invokedAttributes)) return;
            var attrType = a.GetType();
            var method = attrType.GetMethod("Customize");
            if(method == null) throw new NullReferenceException($"Couldn't find Customize method in {attrType.FullName}");
            method.Invoke(a, new object[]{ this });
            var visibilityAttr = a.GetType().GetCustomAttribute<VisibilityAttribute>();
            if (visibilityAttr != null && visibilityAttr.Visible)
            {
                _invokedAttributes.Add(a.GetType());
                return;
            }
            _hiddenAttributes.Add(a.GetType());
        });
    }

    public List<Customizer> ApplyTheOnlyPolicy(List<Customizer> customizers)
    {
        List<Customizer> resultCustomizers = new ArrayList<>(customizers.size());
        Collections.copy(resultCustomizers, customizers);
        Stream<Customizer> resultCustomizersStream = resultCustomizers.stream();
        List<Customizer> theOnlys = resultCustomizersStream.filter(customizer -> customizer.getClass().getAnnotation(TheOnly.class) != null);
        List<Customizer> theOnyLasts = theOnlys.stream().(t => t).Select(grp => grp.Last()).ToList();
        for (var i = attributeList.Count; i >= 0 ; i--)
        {
            var attr = attributeList[i];
            if (theOnlys.Contains(attr) && !theOnyLasts.Contains(attr))
            {
                attributeList.Remove(attr);
            }
        }

        return attributeList;
    }

    public List<Class> GetAppliedCustomizations(){
        return _hiddenAttributes;
    }
}
