package org.opensails.sails;

import org.apache.commons.configuration.Configuration;

/**
 * The 'runtime' interface of a Sails application. This is what clients see the
 * application as normally, after it has been initialized. Every
 * {@link org.opensails.sails.event.ISailsEvent} belongs to one of these.
 * 
 * @author aiwilliams
 * 
 */
public interface ISailsApplication {
    Configuration getConfiguration();

    ApplicationContainer getContainer();

    String getName();
}
