package org.opensails.sails.tester.browser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.ValueElement;
import org.opensails.sails.tester.SailsTester;
import org.opensails.sails.util.BleedingEdgeException;

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
	protected final ContainerAdapterResolver adapterResolver;
	protected final List<ValueElement> elements;
	protected final SailsTester tester;

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
		tester.nextRequestIsMultipart = true;
		return this;
	}

	public ShamFormFields quickSet(Object... keyValuePairs) {
		throw new BleedingEdgeException("This needs to use the adapters for the values");
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