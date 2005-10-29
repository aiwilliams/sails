package org.opensails.sails;


public interface ISailsApplicationConfigurator {
    /**
     * Called when an ISailsApplication is started
     * 
     * @param application
     */
    void configure(IConfigurableSailsApplication application);
}
