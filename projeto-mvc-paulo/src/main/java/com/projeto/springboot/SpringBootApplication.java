package com.projeto.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableTransactionManagement
@EnableWebMvc
@EntityScan(basePackages =  "com.projeto.model")
@ComponentScan(basePackages = "com.projeto.controller")
@EnableJpaRepositories(basePackages = {"com.projeto.repository"})

public class SpringBootApplication implements WebMvcConfigurer 
{
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}
	
	
	
	
	
}
