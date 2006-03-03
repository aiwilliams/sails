package org.opensails.sails.form;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.model.oem.SingleModelContext;
import org.opensails.sails.validation.oem.SailsValidationEngine;

public class HtmlFormFixture {
    public static HtmlForm create() {
        return new HtmlForm(new RequestContainer(new ApplicationContainer()), new SingleModelContext(null), new FormFields(), new IAdapterResolver() {
            public IAdapter resolve(Class<?> parameterClass, IScopedContainer container) {
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
