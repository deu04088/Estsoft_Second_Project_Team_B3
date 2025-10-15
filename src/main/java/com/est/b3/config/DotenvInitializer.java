package com.est.b3.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // 루트 위치
                .ignoreIfMissing()
                .load();

        Map<String, Object> properties = new HashMap<>();
        dotenv.entries().forEach(entry ->
                properties.put(entry.getKey(), entry.getValue())
        );

        applicationContext.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("dotenv", properties));

        System.out.println("✅ .env loaded (" + properties.size() + " keys)");
    }
}
