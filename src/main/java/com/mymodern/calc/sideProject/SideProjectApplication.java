package com.mymodern.calc.sideProject;

import java.util.regex.Pattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SideProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SideProjectApplication.class, args);
		System.out.println("my test run");
		System.out.println(Pattern.matches("\\d+", "1234")); // true
        System.out.println(Pattern.matches("\\D+", "1234")); // false
	}

}
