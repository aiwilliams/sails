package org.opensails.hibernate;

import java.util.*;

import org.opensails.sails.util.*;

public class InMemoryDatabaseConfiguration implements IHibernateDatabaseConfiguration {
	protected static TimeUniqueIdGenerator uniqueIdProvider = new TimeUniqueIdGenerator();

	public Properties connectionProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		properties.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		properties.setProperty("hibernate.connection.username", "sa");
		properties.setProperty("hibernate.connection.password", "");
		properties.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:" + uniqueIdProvider.next());
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		return properties;
	}
}
