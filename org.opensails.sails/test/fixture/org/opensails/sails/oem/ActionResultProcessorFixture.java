package org.opensails.sails.oem;

import org.opensails.sails.processors.TemplateActionResultProcessor;

public class ActionResultProcessorFixture {
    public static TemplateActionResultProcessor template() {
        return new TemplateActionResultProcessor(null);
    }
}
