/**
 * Classes that are used to convert objects to strings and back again.
 * <p>
 * Any type in a Sails application, including Java built-in types, primitives
 * and wrappers, may end up in HTML or JavaScript somewhere. In an effort to
 * ease development and remove duplication, adapters provide a way for the
 * framework to convert Java things into strings for use in HTML and JavaScript,
 * or whatever string you would like, and then convert them back into objects
 * when a request is made.
 * <p>
 * An IAdapter class is searched out when a new Java type needs to be converted.
 * The IAdapter can claim it's ApplicationScope, and if it is not
 * ApplicationScope.SERVLET, a new instance is created, using dependency
 * injection, during each request, thereby allowing request-scoped dependencies
 * (handy for persistence stuff, I tell ya). You should place your IAdapters in
 * a package named, by convention, &lt;package your configurator is
 * in&gt;.adapters. Name it so that it is &lt;your type name&gt;Adapter.
 * <p>
 * If you need access to an IAdapter in a controller, component, or anywhere you
 * have a class that is created by or references the dependency injection
 * container, depend on or obtain the instance of ContainerAdapterResolver.
 */
package org.opensails.sails.adapter;
