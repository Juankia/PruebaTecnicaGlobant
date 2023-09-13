package com.globant.pruebatecnica;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PruebatecnicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebatecnicaApplication.class, args);
	}

	@Bean
	public OpenAPI myOpenAPI() {

		return new OpenAPI().info( new Info()
			.title("Prueba Tecnica")
			.version("1.0.0")
			.description("Proceso de ingreso de cliente cuentas y movimientos")
			.termsOfService("Globant")
			.license(new License().name("Apche 2.0").url("http://pringdoc.org")));
	}
}
