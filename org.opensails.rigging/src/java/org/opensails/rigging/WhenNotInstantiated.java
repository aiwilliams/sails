package org.opensails.rigging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows components to declare to Rigging that when their dependencies are
 * available but not instantiated, it should create an implementation of the
 * provided value.
 * <p>
 * This annotation can only be used on constructor parameter declarations. For
 * instance:
 * <code>
 *   public MyThing(@WhenNotInstantiated(ASpecialMyOtherThing) MyOtherThing iDependOnItButIfItsNotThereUseSpecial) {}
 * </code>
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface WhenNotInstantiated {
    Class value();
}
