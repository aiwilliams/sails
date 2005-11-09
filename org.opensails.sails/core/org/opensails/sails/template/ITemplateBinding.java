package org.opensails.sails.template;

public interface ITemplateBinding {
    void mixin(Class<?> target, Object behavior);

    void mixin(Object behavior);

    void put(String key, Object object);

    void setExceptionHandler(IExceptionHandler exceptionHandler);
}