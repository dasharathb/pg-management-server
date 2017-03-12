package com.bas.pgm;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.bas.pgm*")
@SpringBootApplication
public class Application {
	static final Logger logger = Logger.getLogger(Application.class);
	public static void main(String[] args) {
		logger.info("pg management Application Starts....");
		
		SpringApplication.run(Application.class, args);
		
		logger.info("pg management Application Started....");
	}
}
