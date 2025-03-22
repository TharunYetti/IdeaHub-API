package com.tharunyetti.IdeaHub;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdeaHubApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		System.setProperty("CLIENT-ID", dotenv.get("CLIENT-ID"));
		System.setProperty("CLIENT-SECRET", dotnev.get("CLIENT-SECRET"));
		System.setProperty("DB-USER", dotenv.get("DB-USER"));
		System.setProperty("DB-PWD", dotenv.get("DB-PWD"));

		SpringApplication.run(IdeaHubApplication.class, args);
	}

}
