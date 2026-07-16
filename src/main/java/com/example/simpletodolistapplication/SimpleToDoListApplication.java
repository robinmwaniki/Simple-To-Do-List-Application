package com.example.simpletodolistapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleToDoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleToDoListApplication.class, args);
        System.out.println("=========");
        System.out.println("Simple to do list Application");
        System.out.println("========");
    }

}
