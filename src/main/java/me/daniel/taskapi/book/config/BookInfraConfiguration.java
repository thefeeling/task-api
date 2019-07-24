package me.daniel.taskapi.book.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "me.daniel.taskapi.book")
public class BookInfraConfiguration { }
