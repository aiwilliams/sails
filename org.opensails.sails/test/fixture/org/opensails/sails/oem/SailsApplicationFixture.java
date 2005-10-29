package org.opensails.sails.oem;

import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.SailsApplicationConfiguratorFixture;

public class SailsApplicationFixture {
    /**
     * @return an ISailsApplication configured by
     *         {@link SailsApplicationConfiguratorFixture#configure(IConfigurableSailsApplication)}
     */
    public static ISailsApplication basic() {
        SailsApplication application = new SailsApplication();
        SailsApplicationConfiguratorFixture.configure(application);
        return application;
    }
    
    public static ISailsApplication configured(ISailsApplicationConfigurator configurator) {
        SailsApplication application = new SailsApplication();
        SailsApplicationConfiguratorFixture.configure(application, configurator);
        return application;
        
    }
}
