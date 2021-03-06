package com.example.alura.controlefinancas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableSwagger2
public class ControleFinancasApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleFinancasApiApplication.class, args);
    }

}
