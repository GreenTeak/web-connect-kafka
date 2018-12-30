package com.exampleAPI.zooKeeperAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ZooKeeperApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooKeeperApiApplication.class, args);
	}
}
