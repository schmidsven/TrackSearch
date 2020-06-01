package io.sfrei.tracksearch_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class TracksearchWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TracksearchWebApplication.class, args);
	}

}
