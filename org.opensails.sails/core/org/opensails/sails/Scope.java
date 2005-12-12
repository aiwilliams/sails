package org.opensails.sails;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to declare the ApplicationScope of Sails components.
 * 
 * Various pieces of a Sails application can exist at different lifecycle
 * scopes. Likely the best example of this would be
 * {@link org.opensails.sails.adapter.IAdapter} instances.
 * 
 * @see org.opensails.sails.ApplicationScope
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {
	ApplicationScope value();
}
