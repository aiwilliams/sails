package org.opensails.sails.tester.browser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.ValueElement;
import org.opensails.sails.tester.SailsTester;

/**
 * Used to construct the fields to be submitted in a post or get.
 * <p>
 * NOTE: This needs further development
 * <p>
 * Provides the ability to construct form fields from objects using the
 * IAdapters of your application. This is important. Not using the adapters is
 * repeating yourself.
 * 
 * @author aiwilliams
 */
public class ShamFormFields extends FormFields {
	protected ContainerAdapterResolver adapterResolver;
	protected List<ValueElement> elements;

	@Deprecated
	protected SailsTester tester;
	protected Browser browser;

	public ShamFormFields(Browser browser, ContainerAdapterResolver adapterResolver) {
		this.browser = browser;
		this.adapterResolver = adapterResolver;
		this.elements = new ArrayList<ValueElement>();
	}

	public ShamFormFields(SailsTester tester, ContainerAdapterResolver adapterResolver) {
		this.tester = tester;
		this.adapterResolver = adapterResolver;
		this.elements = new ArrayList<ValueElement>();
	}

	/**
	 * Adds a FileUpload to the FormFields.
	 * 
	 * @param fileFieldName
	 * @param upload
	 */
	public void addFile(String fileFieldName, ShamFileUpload upload) {
		backingMap.put(fileFieldName, upload);
	}

	public ShamFormFields multipart() {
		if (browser != null) browser.nextRequestIsMultipart = true;
		if (tester != null) tester.nextRequestIsMultipart = true;
		return this;
	}

	@SuppressWarnings("unchecked")
	public ShamFormFields quickSet(Object... keyValuePairs) {
		if (keyValuePairs.length % 2 != 0) throw new IllegalArgumentException("Must provide key value pairs. You have given an odd number of arguments.");
		for (int i = 0; i < keyValuePairs.length; i += 2) {
			String key = (String) keyValuePairs[i];
			Object value = keyValuePairs[i + 1];
			Class<? extends Object> valueType = value.getClass();
			IAdapter adapter = adapterResolver.resolve(valueType);
			Object adaptedValue = adapter.forWeb(valueType, value);
			setValue(key, adaptedValue);
		}
		return this;
	}

	/**
	 * @see #setSubmitAction(String, List)
	 * @param action
	 */
	public void setSubmitAction(String action) {
		setSubmitAction(action, ListUtils.EMPTY_LIST);
	}

	/**
	 * TODO: Make this useful.
	 * 
	 * Adds a 'meta-submit'.
	 * <p>
	 * This allows you to avoid knowing the details of how Sails provides for
	 * multiple submit buttons on an HTML form. Since there can be only one
	 * action invoked, setting this will overwrite the previous value.
	 * 
	 * @param action
	 */
	public void setSubmitAction(String action, List parameters) {
		add(new Submit("", adapterResolver).action(action, parameters));
	}

	protected <T extends ValueElement> T add(T element) {
		elements.add(element);
		return element;
	}
}