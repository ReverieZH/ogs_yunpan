package com.ogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Start {

    public static void main(String[] args) {
        System.out.println("init...");
        SpringApplication.run(Start.class);
        System.out.println("init finished...");
    }
}
