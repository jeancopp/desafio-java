package br.com.coppieters.concrete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.coppieters")
public class ConcreteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcreteApplication.class, args);
	}

}

