package exdevlab.instantiation.customization;

import java.util.List;

public interface ICancellable
{
    Boolean HasToBeCanceled(List<Class> invocationList);
}
