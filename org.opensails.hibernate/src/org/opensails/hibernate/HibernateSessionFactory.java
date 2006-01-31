package org.opensails.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.opensails.rigging.*;

public class HibernateSessionFactory implements Disposable {
    protected AnnotationConfiguration configuration;
    protected SessionFactory sessionFactory;

    public HibernateSessionFactory(IHibernateDatabaseConfiguration databaseConfiguration, IHibernateMappingConfiguration mappingConfiguration) {
        setupConfiguration(databaseConfiguration);
        setupMappings(mappingConfiguration);
    }

    public void closeFactory() {
        if (sessionFactory != null)
            sessionFactory.close();
    }

    public void dispose() {
        closeFactory();
    }

    public Session openSession() {
        return getSessionFactory().openSession();
    }

    public Session openSession(Interceptor interceptor) {
        return getSessionFactory().openSession(interceptor);
    }

    public StatelessSession openStatelessSession() {
        return getSessionFactory().openStatelessSession();
    }

    protected synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }

    protected void setupConfiguration(IHibernateDatabaseConfiguration databaseConfiguration) {
        this.configuration = new AnnotationConfiguration();
        configuration.addProperties(databaseConfiguration.connectionProperties());
        databaseConfiguration.configure(configuration);
    }

    protected void setupMappings(IHibernateMappingConfiguration mappingConfiguration) {
        for (Class mappedClass : mappingConfiguration.mappedClasses())
            configuration.addClass(mappedClass);
        for (Class annotatedClass : mappingConfiguration.annotatedClasses())
            configuration.addAnnotatedClass(annotatedClass);
    }
}
