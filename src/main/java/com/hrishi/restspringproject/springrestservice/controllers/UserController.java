package com.hrishi.restspringproject.springrestservice.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hrishi.restspringproject.springrestservice.beans.User;
import com.hrishi.restspringproject.springrestservice.dao.UserDAO;
import com.hrishi.restspringproject.springrestservice.exception.UserNotFoundException;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDao;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userDao.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable int id) {
		User user = userDao.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("User not found with id=" + id);
		}
		
		return user;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDao.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException("User not found with id=" + id);
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User newUser = userDao.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
