package com.example.rdsreconnectdemo;

import java.security.Security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RdsReconnectDemoApplication {

	public static void main(String[] args) {
		
		Security.setProperty("networkaddress.cache.ttl" , "2");
		Security.setProperty("networkaddress.cache.negative.ttl", "0");
		
		SpringApplication.run(RdsReconnectDemoApplication.class, args);
	}

}
