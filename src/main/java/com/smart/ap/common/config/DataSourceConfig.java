package com.smart.ap.common.config;

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.smart.ap.repository" , entityManagerFactoryRef = "basicEntityManager", transactionManagerRef = "basicTransactionManager")
public class DataSourceConfig  {

	@Autowired
	private Environment env;

	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean basicEntityManager() throws SQLException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("spring.jpa.properties.hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
		properties.put("spring.jpa.properties.hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.put("spring.jpa.properties.hibernate.use_sql_comments", env.getProperty("spring.jpa.properties.hibernate.use_sql_comments"));
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
		properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName()); // 네이밍
		properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName()); // 네이밍

		em.setDataSource(basicDataSource());
		em.setPackagesToScan("com.smart.ap.entity");
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaPropertyMap(properties);
		em.setPersistenceUnitName("basic");
		return em;
	}

	@Primary
	@Bean
    public DataSource basicDataSource() throws SQLException {
		if ("true".equals(env.getProperty("database.jndi"))) {
			JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
			return dataSourceLookup.getDataSource(env.getProperty("database.jndi.name"));
		} else {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl(env.getProperty("database.url"));
			dataSource.setDriverClassName(env.getProperty("database.className"));
			dataSource.setUsername(env.getProperty("database.user"));
			dataSource.setPassword(env.getProperty("database.pwd"));
			return dataSource;
		}
    }

	@Primary
	@Bean
    public PlatformTransactionManager basicTransactionManager() throws SQLException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(basicEntityManager().getObject());
		return transactionManager;
	}

}
