package com.Udea.Ciclo3;

import com.Udea.Ciclo3.modelos.Enterprise;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication (exclude ={SecurityAutoConfiguration.class} )
public class Ciclo3Application {

	@GetMapping ("/hello")
	public String hello(){
		return "Hola Ciclo 3... Saldremos vivos de esto!";
	}

	@GetMapping ("/test")
	public String test(){
		Enterprise emp = new Enterprise("Espada De Bolivar","calle 48 a sur n 88C", "7774678" , "122548965-3");
		emp.setName("Espada de Bolivar Ltda");

		System.out.println("Aqui ya se creo la empresa y se renombro");
		return emp.getName();

	}




	public static void main(String[] args) {
		SpringApplication.run(Ciclo3Application.class, args);

	}

}
