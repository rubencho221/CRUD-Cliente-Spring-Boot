package com.sprinboot.clienteapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ClienteAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ClienteAppApplication.class, args);
	}
}
