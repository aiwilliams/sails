package org.opensails.sails.action;


public interface IActionResultProcessor<T extends IActionResult> {
    void process(T result);
}
