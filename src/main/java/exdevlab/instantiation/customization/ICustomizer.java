package exdevlab.instantiation.customization;

public interface ICustomizer<TCustomizationTarget> {
    void Customize(TCustomizationTarget context);
}
