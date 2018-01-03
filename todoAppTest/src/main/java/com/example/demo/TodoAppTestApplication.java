package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ToDoRepository;


@SpringBootApplication
public class TodoAppTestApplication {

	//@Autowired
	//private  ToDoRepository tdrep;
	
	public static void main(String[] args) {
		SpringApplication.run(TodoAppTestApplication.class, args);
	}
}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${message:Hello defaulta}")
    private String message;

    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
}