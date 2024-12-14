package com.example.demo;

import com.example.demo.repository.VkRepository;
import com.example.demo.service.VkService;
import com.example.demo.util.Parser;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

@SpringBootApplication
public class JavaRtfApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaRtfApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Parser parser() {
		return new Parser();
	}

	@Bean
	public VkRepository VkRepository() { return new VkRepository(); }
}
