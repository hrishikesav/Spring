package com.hrishi.restspringproject.springrestservice.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hrishi.restspringproject.springrestservice.beans.User;

@Component
public class UserDAO {

	private static List<User> users = new ArrayList<>();
	
	private static int userIdCounter = 3;
	
	static {
		users.add(new User(1, "Saul", new Date()));
		users.add(new User(2, "Goodman", new Date()));
		users.add(new User(3, "McGill", new Date()));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++userIdCounter);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		for(User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(int id) {
		Iterator<User> userIterator = users.iterator();
		while(userIterator.hasNext()) {
			User user = userIterator.next();
			if(user.getId() == id) {
				userIterator.remove();
				return user;
			}
		}
		return null;
	}
	
	
	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

}
