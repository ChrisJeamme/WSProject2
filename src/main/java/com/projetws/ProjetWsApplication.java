package com.projetws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.projetws.tools.StorageService;

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

