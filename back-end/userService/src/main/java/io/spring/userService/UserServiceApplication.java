package io.spring.userService;

import io.spring.userService.entity.Role;
import io.spring.userService.entity.User;
import io.spring.userService.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class UserServiceApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
