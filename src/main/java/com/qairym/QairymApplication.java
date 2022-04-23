package com.qairym;

import java.util.ArrayList;
import java.util.Arrays;

import com.qairym.entities.City;
import com.qairym.repositories.CityRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QairymApplication {

	public static void main(String[] args) {
		SpringApplication.run(QairymApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CityRepository cityRepository) {
		return args -> {
			cityRepository.saveAll(
				new ArrayList<>(
					Arrays.asList(
						new City(null, "Нур-Султан", null),
						new City(null, "Шымкент", null),
						new City(null, "Усть-Cumеногорск", null),
						new City(null, "Алматы", null),
						new City(null, "Уральск", null)
				))
			);
		};
	}
}
