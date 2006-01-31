package org.opensails.hibernate;

import java.util.*;

import org.hibernate.cfg.*;

public interface IHibernateDatabaseConfiguration {
    /**
     * Hook to allow serious Configuration customization.
     * <p>
     * This is invoked after the connectionProperties have been obtained and
     * used.
     * 
     * @param configuration
     */
    void configure(AnnotationConfiguration configuration);

    Properties connectionProperties();
}
