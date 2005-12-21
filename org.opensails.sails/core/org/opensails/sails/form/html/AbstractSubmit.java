package org.opensails.sails.form.html;

import static org.opensails.sails.form.FormMeta.ACTION_PREFIX;

import java.util.List;

import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;

/**
 * Submits an HTML form somehow. These types have much of the same behaviour
 * w/r/t actions and parameters.
 * 
 * One of the thoughts that I had after looking at the way ASP.NET works was
 * having the FormMixin output a hidden field at the bottom of the form that
 * included information that could be used on the server side to determine which
 * action to execute. After some consideration, I decided that, for now, it is
 * preferable to not have a dependency on the FormMixin. Therefore, the name is
 * utilized.
 * 
 * If the form author does not declare the action on the Submit (using
 * #action(String)), then they must make the form action attribute point to the
 * action. Normally, it would point to the controller that rendered the form.
 */
public abstract class AbstractSubmit<T extends AbstractSubmit> extends InputElement<T> {
	protected String action;
	protected ContainerAdapterResolver adapterResolver;
	protected List parameters;

	/**
	 * @param adapterResolver used when submit is created with parameters
	 * @param name
	 */
	public AbstractSubmit(String typeValue, String name, ContainerAdapterResolver adapterResolver) {
		super(typeValue, name);
		this.adapterResolver = adapterResolver;
	}

	@SuppressWarnings("unchecked")
	public T action(String action) {
		this.action = action;
		return (T) this;
	}

	public T action(String action, List parameters) {
		this.parameters = parameters;
		return action(action);
	}

	public String getAction() {
		return action;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getName() {
		if (action != null) {
			StringBuilder name = new StringBuilder();
			name.append(ACTION_PREFIX);
			name.append(action);
			if (parameters != null && !parameters.isEmpty()) {
				for (Object parameter : parameters) {
					IAdapter adapter = adapterResolver.resolve(parameter.getClass());
					name.append("_");
					name.append(adapter.forWeb(parameter.getClass(), parameter));
				}
			}
			return name.toString();
		} else return super.getName();
	}
}
