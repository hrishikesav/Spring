package com.hrishi.restspringproject.springrestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrishi.restspringproject.springrestservice.beans.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
