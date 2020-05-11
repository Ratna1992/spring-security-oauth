package com.security.oauth2.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class MySQLDataSourceConfig {
	@Autowired
	Environment prop;

	@Bean
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder.create().driverClassName(prop.getProperty("spring.datasource.driver-class-name"))
				.password(prop.getProperty("spring.datasource.password")).url(prop.getProperty("spring.datasource.url"))
				.username(prop.getProperty("spring.datasource.data-username")).build();

	}
}
