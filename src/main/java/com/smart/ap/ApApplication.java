package com.smart.ap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = { "com.smart.ap" })
public class ApApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.servlet-path", "/*");
		System.setProperty("server.servlet.context-path", "/");
		SpringApplication.run(ApApplication.class, args);
	}
}
