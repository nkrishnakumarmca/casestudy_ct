/**
 * Copyright (c) 2019, ABC Ltd.
 */
package com.rb.stmt.validator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * Class which initializes all the configuration needed for the application
 * 
 * @author
 *
 */
@Configuration
@PropertySource(value = "classpath:config.properties")
public class AppConfig {

	// Place to create the required beans
}
