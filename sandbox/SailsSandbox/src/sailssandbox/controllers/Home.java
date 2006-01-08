package sailssandbox.controllers;

import org.opensails.sails.controller.oem.BaseController;

public class Home extends BaseController {
    public void index() {
        expose("message", "helloWorld!");
    }

    public void edit(Long id) {
        expose("id", id);
    }
}
