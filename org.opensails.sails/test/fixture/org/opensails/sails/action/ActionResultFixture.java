package org.opensails.sails.action;

import org.opensails.sails.action.oem.TemplateActionResult;
import org.opensails.sails.event.oem.SailsEventFixture;

public class ActionResultFixture {
    /**
     * @return a TemplateActionResult
     */
    public static TemplateActionResult template() {
        return new TemplateActionResult(SailsEventFixture.sham());
    }
}
