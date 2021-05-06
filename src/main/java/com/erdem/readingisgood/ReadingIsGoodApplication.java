package com.erdem.readingisgood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.erdem")
@SpringBootApplication
public class ReadingIsGoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadingIsGoodApplication.class, args);
    }

}
