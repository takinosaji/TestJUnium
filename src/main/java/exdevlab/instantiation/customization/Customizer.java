package exdevlab.instantiation.customization;

import exdevlab.instantiation.IncorrectInheritanceException;
import exdevlab.instantiation.customization.prioritizing.Priority;
import java.util.ArrayList;
import java.util.List;

public class Customizer implements ICancellable {
    private Class _targetType;
    public int Priority;
    public Boolean Visible;
    public List<Class> CancellationList;

    protected Customizer(Class targetType) throws IncorrectInheritanceException {
        this(targetType, null, 0);
    }

    protected Customizer(Class targetType, int priority) throws IncorrectInheritanceException {
        this(targetType, null, priority);
    }

    protected Customizer(Class targetType, List<Class> cancellationCollection) throws IncorrectInheritanceException {
        this(targetType, cancellationCollection, 0);
    }

    protected Customizer(Class targetType, List<Class> cancellationCollection, int priority) throws IncorrectInheritanceException {
        if (!ICustomizationAttributeDrivenTest.class.isAssignableFrom(targetType))
            throw new IncorrectInheritanceException(new String[] {targetType.getTypeName()}, new String[]{ICustomizationAttributeDrivenTest.class.getTypeName()});
        if (cancellationCollection == null)
        {
            cancellationCollection = new ArrayList<>();
        }
        _targetType = targetType;
        Visible = true;
        CancellationList = cancellationCollection;
        Priority anno = getClass().getAnnotation(Priority.class);
        if (anno != null)
        {
            Priority = anno.Value();
            return;
        }
        Priority = priority;
    }

    public Boolean HasToBeCanceled(List<Class> invocationList)
    {
        boolean[] result = {false};
        CancellationList.forEach(cancelItem ->
                invocationList.forEach(invItem ->
                {
                    if(cancelItem.getName().equals(invItem.getName()))
                    {
                        result[0] = true;
                    }
                }));
        return result[0];
    }

    public Class GetCustomizationTargetType(){
        return _targetType;
    }
}
