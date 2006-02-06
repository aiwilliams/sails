package org.opensails.dock;

import org.opensails.sails.util.DevelopmentBoot;

public class JettyBoot extends DevelopmentBoot {
    public static void main(String[] args) {
        new JettyBoot().startServer();
    }
    
    @Override
    protected String contextDirectory() {
        return "./app";
    }
}