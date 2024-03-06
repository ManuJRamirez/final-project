package com.wallaclone.finalproject;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
}