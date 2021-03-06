package org.opensails.sails.template;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.opensails.sails.annotate.Behavior;
import org.opensails.sails.annotate.oem.CacheHandler;
import org.opensails.sails.tools.CacheTool;

/**
 * Marks individual actions or whole controllers as being cached.
 * <p>
 * Generating a response to a client can be expensive. When it is content that
 * is not unique per visitor, caching provides a solution. Annotate an action as
 * Cached, specifying it's CacheType - the default is CacheType.ACTION.
 * <p>
 * Note that you may also cache only fragments of documents. Please look at the
 * {@link CacheTool} for more information.
 * 
 * @see CacheType
 * 
 * @author aiwilliams
 */
@Documented
@Behavior(CacheHandler.class)
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface Cached {
	CacheType value() default CacheType.ACTION;
}
