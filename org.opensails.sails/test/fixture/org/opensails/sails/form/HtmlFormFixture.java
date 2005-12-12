package org.opensails.sails.form;

import org.opensails.rigging.*;
import org.opensails.sails.*;
import org.opensails.sails.adapter.*;
import org.opensails.sails.model.oem.*;
import org.opensails.sails.validation.oem.*;

public class HtmlFormFixture {
	public static HtmlForm create() {
		return new HtmlForm(new RequestContainer(new ScopedContainer(ApplicationScope.SERVLET)), new SingleModelContext(null), new FormFields(), new IAdapterResolver() {
			public IAdapter resolve(Class<?> parameterClass, ScopedContainer container) {
				return null;
			}
		}, new SailsValidationEngine());
	}

	public static HtmlForm invalid() {
		return new HtmlForm(null, null, null, null, null) {
			@Override
			public String getMessage() {
				return "simulated invalid";
			}

			@Override
			public boolean isValid() {
				return false;
			}
		};
	}
}
