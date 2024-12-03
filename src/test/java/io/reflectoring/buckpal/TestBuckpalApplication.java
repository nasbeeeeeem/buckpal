package io.reflectoring.buckpal;

import org.springframework.boot.SpringApplication;

public class TestBuckpalApplication {

	public static void main(String[] args) {
		SpringApplication.from(BuckpalApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
