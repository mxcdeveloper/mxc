package com.xavier.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.xavier.config.env.EnvConfiguration;

import ch.qos.logback.core.db.DriverManagerConnectionSource;

@Configuration
@Component
public class DatabaseConfig {

	@Autowired
	EnvConfiguration envConfiguration;

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(envConfiguration.getDatabaseDriver());
		ds.setUrl("jdbc:postgresql://"+envConfiguration.getDatabaseIP()+":"+envConfiguration.getDatabasePort()+"/"+envConfiguration.getDatabaseName()+"?autoReconnect=true&useSSL=false");
		System.out.println("...."+"jdbc:postgresql://"+envConfiguration.getDatabaseIP()+":"+envConfiguration.getDatabasePort()+"/"+envConfiguration.getDatabaseName()+"?autoReconnect=true&useSSL=false");
		ds.setUsername(envConfiguration.getDatabaseUser());
		ds.setPassword(envConfiguration.getDatabasePassword());
		return ds;
	}
}
