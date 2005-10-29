package org.opensails.sails;

import org.opensails.sails.controller.IActionResult;

public interface IActionResultProcessorResolver {
    IActionResultProcessor resolve(IActionResult result);
}
