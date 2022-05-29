package com.qairym;

import com.qairym.entities.City;
import com.qairym.entities.Role;
import com.qairym.repositories.CityRepository;
import com.qairym.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableSwagger2
public class QairymApplication {

	public static void main(String[] args) {
		SpringApplication.run(QairymApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CityRepository cityRepository, UserService userService) {
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

			userService.saveRole(new Role(null, "ROLE_USER"));
		};
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}
