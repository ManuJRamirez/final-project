package com.wallaclone.finalproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        Properties properties = loadProperties();
        properties.forEach((key, value) -> System.setProperty((String) key, (String) value));

        return application.sources(FinalProjectApplication.class);
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream externalResource = new FileInputStream("/home/apps/tomcat/conf/application.properties");
            properties.load(externalResource);
        } catch (IOException e) {
        	try (InputStream internalResource = getClass().getClassLoader().getResourceAsStream("application.properties")) {
                if (internalResource != null) {
                    properties.load(internalResource);
                } else {
                    System.err.println("No se pudo cargar el archivo application.properties ni desde la ubicación externa ni desde la interna.");
                }
            } catch (IOException ex) {
                System.err.println("Error al cargar el archivo application.properties: " + ex.getMessage());
            }
        }
        return properties;
    }

}
