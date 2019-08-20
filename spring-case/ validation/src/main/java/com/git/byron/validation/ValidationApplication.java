package com.git.byron.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;

@SpringBootApplication
public class ValidationApplication {


    @Bean
    public BeanValidationPostProcessor beanValidationPostProcessor(){
        return new BeanValidationPostProcessor();
    }

    public static void main(String[] args) {
        SpringApplication.run(ValidationApplication.class, args);
    }



}
