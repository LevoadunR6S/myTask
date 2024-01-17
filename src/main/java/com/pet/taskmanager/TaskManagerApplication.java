package com.pet.taskmanager;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.pet.taskmanager.repository")
public class TaskManagerApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

}
