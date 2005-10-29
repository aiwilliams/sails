package org.opensails.sails.controller.oem;

import org.opensails.sails.oem.SailsEventFixture;

public class ActionResultFixture {
    /**
     * @return a TemplateActionResult
     */
    public static TemplateActionResult template() {
        return new TemplateActionResult(SailsEventFixture.sham());
    }
}
