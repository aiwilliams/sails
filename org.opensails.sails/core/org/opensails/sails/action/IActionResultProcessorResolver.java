package org.opensails.sails.action;


public interface IActionResultProcessorResolver {
    IActionResultProcessor resolve(IActionResult result);
}
