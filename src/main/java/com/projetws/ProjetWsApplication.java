package com.projetws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetWsApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ProjetWsApplication.class, args);
	}
	/*
	  @Bean
	    CommandLineRunner init(StorageService storageService) {
	        return (args) -> {
	            //storageService.deleteAll();
	            storageService.init();
	        };
	    }*/
}

