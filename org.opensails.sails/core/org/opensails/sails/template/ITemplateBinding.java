package org.opensails.sails.template;

public interface ITemplateBinding {
    void mixin(Class<?> target, Object helper);

    void mixin(Object helper);

    void put(String key, Object object);

    void setExceptionHandler(IExceptionHandler exceptionHandler);
}