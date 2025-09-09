package com.think_different.think_different;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThinkDifferentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkDifferentApplication.class, args);
    }

}
