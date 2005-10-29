package org.opensails.sails;

import org.opensails.sails.controller.IActionResult;

public interface IActionResultProcessor<T extends IActionResult> {
    void process(T result);
}
