package org.opensails.sails;

/**
 * The Sails Web Application Framework classes and interfaces that represent the
 * 'entry points' of it's architecture.
 * 
 * <h1>Welcome to Sails</h1>
 * Sails splits the response to a web request into a controller part (performing
 * the logic) and a view part (rendering a template). This two-step approach is
 * known as an action, which will normally create, read, update, or delete (CRUD
 * for short) some sort of model part (often backed by a database) before
 * choosing either to render a template or redirecting to another action.
 * 
 * Sails implements these actions as public methods on Action Controllers and
 * uses Action Views to implement the template rendering. Action Controllers are
 * then responsible for handling all the actions relating to a certain part of
 * an application. This grouping usually consists of actions for lists and for
 * CRUDs revolving around a single (or a few) model objects. So
 * ContactController would be responsible for listing contacts, creating,
 * deleting, and updating contacts. A WeblogController could be responsible for
 * both posts and comments.
 * 
 * Action View templates are written using Viento statements mingled in with the
 * HTML. To avoid cluttering the templates with code, a bunch of template mixin
 * classes provide common behavior for forms, dates, and strings. And it's easy
 * to add specific mixins to keep the separation as the application evolves.
 * 
 * <h4>Actions</h4>
 * Actions are methods grouped in a controller instead of separate command
 * objects. These actions share behavior that is common to them.
 * 
 * <code><pre>
 * public class BlogController extends BaseController {
 * 	public void display(Customer customer) {
 * 		expose(&quot;customer&quot;, customer);
 * 	}
 * 
 * 	public void update(Customer customer) {
 * 		if (updateModel(customer)) {
 * 			persister.save(customer);
 * 			redirectAction(&quot;display&quot;);
 * 		} else renderTemplate(&quot;edit&quot;);
 * 	}
 * }
 * </pre></code>
 * 
 * <h2>Getting Started</h2>
 * TODO: Document Getting Started
 * <p>
 * <h4>Some gritty details</h4>
 * Sails is essentially a Servlet application. HTTP events are transformed into
 * ISailsEvents of various kinds, then dispatched to the appropriate
 * IActionEventProcessor (controller, component). The processor typically
 * delegates to an IAction, usually implemented as a method on an
 * IEventProcessingContext (a controller or component implementation class).
 */
