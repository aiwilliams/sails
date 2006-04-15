package org.opensails.spyglass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides a way to map Strings or Classes to Classes within a package.
 * 
 * @author aiwilliams
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface ClassKeyMappings {
	Mapping[] value();
}
