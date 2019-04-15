/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot starter class
 * 
 * @author
 *
 */

@SpringBootApplication
public class DataValidatorApplication {

	private static final Logger logger = LoggerFactory.getLogger(DataValidatorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DataValidatorApplication.class, args);
		logger.info("Application started...");
	}
}
