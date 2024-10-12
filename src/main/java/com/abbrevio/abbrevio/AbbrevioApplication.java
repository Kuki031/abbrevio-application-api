package com.abbrevio.abbrevio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.abbrevio.abbrevio.entity")
@EnableJpaRepositories(basePackages = "com.abbrevio.abbrevio.repository")
public class AbbrevioApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbbrevioApplication.class, args);
    }

}
