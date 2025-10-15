package com.est.b3;

import com.est.b3.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class B3Application {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(B3Application.class);
		app.addInitializers(new DotenvInitializer());
		app.run(args);
	}

}
