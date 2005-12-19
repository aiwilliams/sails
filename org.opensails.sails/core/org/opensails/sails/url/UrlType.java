package org.opensails.sails.url;

import org.opensails.sails.url.oem.BuiltinScriptUrlResolver;
import org.opensails.sails.url.oem.BuiltinStyleUrlResolver;
import org.opensails.sails.url.oem.ComponentUrlResolver;
import org.opensails.sails.url.oem.ContextUrlResolver;
import org.opensails.sails.url.oem.ControllerUrlResolver;
import org.opensails.sails.url.oem.ImageUrlResolver;
import org.opensails.sails.url.oem.ScriptUrlResolver;
import org.opensails.sails.url.oem.StyleUrlResolver;

/**
 * Used to resolve urls within the application. You can create your own! Just
 * subclass, calling the constructor and passing the class of your type's
 * IUrlResolver. The framework will instantiate one when folks attempt to
 * resolve your types of urls.
 */
public class UrlType {
	public static final UrlType CONTEXT = new UrlType(ContextUrlResolver.class);
	public static final UrlType COMPONENT = new UrlType(ComponentUrlResolver.class);
	public static final UrlType CONTROLLER = new UrlType(ControllerUrlResolver.class);
	public static final UrlType IMAGE = new UrlType(ImageUrlResolver.class);
	public static final UrlType SCRIPT = new UrlType(ScriptUrlResolver.class);
	public static final UrlType SCRIPT_BUILTIN = new UrlType(BuiltinScriptUrlResolver.class);
	public static final UrlType STYLE = new UrlType(StyleUrlResolver.class);
	public static final UrlType STYLE_BUILTIN = new UrlType(BuiltinStyleUrlResolver.class);

	protected final Class<? extends IUrlResolver> resolverClass;

	protected UrlType(Class<? extends IUrlResolver> resolverClass) {
		this.resolverClass = resolverClass;
	}

	public Class<? extends IUrlResolver> getResolverClass() {
		return resolverClass;
	}
}
