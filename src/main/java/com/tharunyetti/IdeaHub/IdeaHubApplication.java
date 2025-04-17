package com.tharunyetti.IdeaHub;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdeaHubApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		System.setProperty("CLIENT-ID", System.getenv("CLIENT-ID"));
		System.setProperty("CLIENT-SECRET", System.getenv("CLIENT-SECRET"));
		System.setProperty("DB-USER", System.getenv("DB-USER"));
		System.setProperty("DB-PWD", System.getenv("DB-PWD"));

		SpringApplication.run(IdeaHubApplication.class, args);
	}

}
