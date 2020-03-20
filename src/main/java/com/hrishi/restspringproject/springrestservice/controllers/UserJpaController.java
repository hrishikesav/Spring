package com.hrishi.restspringproject.springrestservice.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.hrishi.restspringproject.springrestservice.beans.Post;
import com.hrishi.restspringproject.springrestservice.beans.User;
import com.hrishi.restspringproject.springrestservice.dao.UserDAO;
import com.hrishi.restspringproject.springrestservice.exception.PostNotFoundException;
import com.hrishi.restspringproject.springrestservice.exception.UserNotFoundException;
import com.hrishi.restspringproject.springrestservice.repository.PostRepository;
import com.hrishi.restspringproject.springrestservice.repository.UserRepository;

@RestController
public class UserJpaController {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public User getUser(@PathVariable int id) {
		User user = userDao.findOne(id);
		//Optional<User> user = userRepository.findById(id);
		if(user == null) {
			throw new UserNotFoundException("User not found with id=" + id);
		}
		
		return user;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDao.deleteById(id);
		//userRepository.deleteByid();
		if(user == null) {
			throw new UserNotFoundException("User not found with id=" + id);
		}
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User newUser = userDao.save(user);
		//User newUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getAllUsersPosts(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("Id - " + id);
		}
		
		if(userOptional.get().getPosts().isEmpty()) {
			throw new PostNotFoundException("Post not found for User id - " + id);
		}
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("Id - " + id);
		}
		
		User user = userOptional.get();
		
		post.setUser(user);
		
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
