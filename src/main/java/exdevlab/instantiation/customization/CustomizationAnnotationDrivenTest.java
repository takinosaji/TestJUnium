package exdevlab.instantiation.customization;

import exdevlab.instantiation.core.KernelDrivenTest;
import java.util.ArrayList;
import java.util.List;

public class CustomizationAnnotationDrivenTest extends KernelDrivenTest implements ICustomizationAttributeDrivenTest
{
    private List<Class> _invokedAttributes;
    private List<Class> _hiddenAttributes;

    protected CustomizationAttributeDrivenTest()
    {
        _hiddenAttributes = new ArrayList<>();
        _invokedAttributes = new ArrayList<>();
        Kernel.Bind<CustomizationAttributeDrivenTest>().ToConstant(this);
    }

    public void ApplyCustomization()
    {
        var frame = new StackFrame(1);
        var callingMethod = frame.GetMethod();
        var targetType = callingMethod.DeclaringType ?? GetType();
        List<Class> annoList = new List<CustomizationAttribute>(GetType().GetCustomAttributes<CustomizationAttribute>()
        .Where(a => a.GetType().GetInterfaces().Where(i => i.IsGenericType).Any(i => i.GetGenericTypeDefinition() == typeof(ICustomizationAttribute<>)))
        .Where(a => a.GetCustomizationTargetType() == targetType || targetType.IsSubclassOf(a.GetCustomizationTargetType())));
        annoList.sort((o1, o2) -> {
            Class mineTargetType = o1.GetCustomizationTargetType();
            Class othersTargetType = o2.GetCustomizationTargetType();
            if (!typeof(ICustomizationAttributeDrivenTest).IsAssignableFrom(mineTargetType))
                throw new IncorrectCustomizationTargetTypeException(mineTargetType.Name);
            if (!typeof(ICustomizationAttributeDrivenTest).IsAssignableFrom(othersTargetType))
                throw new IncorrectCustomizationTargetTypeException(othersTargetType.Name);
            if (mineTargetType.IsSubclassOf(othersTargetType)) return 1;
            if (othersTargetType.IsSubclassOf(mineTargetType)) return -1;
            return Priority == 0 ? 1 : other.Priority == 0 ? - 1 : Priority - other.Priority;
        });

        attributeList = ApplyTheOnlyPolicy(attributeList);
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

    public List<Customizer> ApplyTheOnlyPolicy(List<Customizer> customizationAttributes)
    {
        var attributeList = customizationAttributes.ToList();
        var theOnlys = attributeList.Where(attr => attr.GetType().GetCustomAttribute<TheOnlyAttribute>() != null).ToList();
        var theOnyLasts = theOnlys.GroupBy(t => t).Select(grp => grp.Last()).ToList();
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
