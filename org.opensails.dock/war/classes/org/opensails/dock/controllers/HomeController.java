package org.opensails.dock.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class HomeController extends BaseController {
    public void index() {
        index("World");
    }
    
    public void index(String who) {
        expose("who", who);
    }
}
