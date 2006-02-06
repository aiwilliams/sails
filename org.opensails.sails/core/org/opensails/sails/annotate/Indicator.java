package org.opensails.sails.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that can be placed on a
 * {@link org.opensails.sails.event.IEventProcessingContext controller or component}
 * to indicate to the framework certain attributes of an action.
 * <p>
 * The best way to gain an understand of this is to look at what is happening in
 * the {@link org.opensails.sails.mixins.UrlforMixin}. When an action is
 * indicated as being Secure, this mixin will resolve the url in such a way as
 * to have the link include 'https', thereby making it a 'secure' action.
 * 
 * @author aiwilliams
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Indicator {
	Class<? extends IIndicatorResolver> value();
}
