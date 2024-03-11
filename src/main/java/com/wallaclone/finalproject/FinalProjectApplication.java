package com.wallaclone.finalproject;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan
@SpringBootApplication

public class FinalProjectApplication {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbPwd;

	@Value("${spring.datasource.driver-class-name}")
	private String datasource;

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(datasource);
		dataSourceBuilder.url(dbUrl);
		dataSourceBuilder.username(dbUser);
		dataSourceBuilder.password(dbPwd);
		return dataSourceBuilder.build();
	}

	@Bean
	public Properties appProperties() throws IOException {
		Properties properties = new Properties();
		String externalPropertiesFile = "/home/apps/tomcat/properties/application.properties";
		Resource externalResource = new ClassPathResource(externalPropertiesFile);
		if (externalResource.exists()) {
			properties.load(externalResource.getInputStream());
		}

		if (properties.isEmpty()) {
			Resource internalResource = new ClassPathResource("application.properties");
			properties.load(internalResource.getInputStream());
			System.out.println("properties "+properties.getProperty("spring.datasource.url"));
		}

		return properties;
	}
}