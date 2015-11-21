package com.vote.web.rest;


import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vote.domain.User;
import com.vote.repository.UserRepository;

@EnableAutoConfiguration
@RestController
@RequestMapping("/user")
public class UserResource {
	
	@Inject
	private UserRepository userRepository;

	@RequestMapping(value = "/{login}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser( @PathVariable String login) {
		User user = new User();
		user.setName("lilei");
		user.setLogin(login);
		user.setPassword("admin");
		userRepository.save(user);
		return user;
	}
	
	
}
