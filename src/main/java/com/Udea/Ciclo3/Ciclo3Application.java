package com.Udea.Ciclo3;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;



@SpringBootApplication
public class Ciclo3Application {

	@GetMapping ("/hello")
	public String hello(){
		return "Hola Ciclo 3... Saldremos vivos de esto!";
	}




	public static void main(String[] args) {
		SpringApplication.run(Ciclo3Application.class, args);

	}

}
