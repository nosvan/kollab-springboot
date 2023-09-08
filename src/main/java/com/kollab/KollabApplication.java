package com.kollab;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

@SpringBootApplication
public class KollabApplication {
	@Autowired
	private ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(KollabApplication.class, args);
	}
}
