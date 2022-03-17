package com.qairym;

import java.util.ArrayList;
import java.util.Arrays;

import com.qairym.entities.City;
import com.qairym.repositories.CityRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
						new City(null, "Алматы", null)
				))
			);
		};
	}
}
