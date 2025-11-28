package com.buyakov.ja.chatop;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.function.Consumer;

@SpringBootApplication
@EnableJpaAuditing
public class ChatopApplication {

	public static void main(String[] args) {
		// Load .env file if it exists
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();
		
		// Set system properties from .env file
		dotenv.entries().forEach(new Consumer<DotenvEntry>() {
            @Override
            public void accept(DotenvEntry entry) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });
		
		SpringApplication.run(ChatopApplication.class, args);
	}

}
