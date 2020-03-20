package com.hrishi.restspringproject.springrestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrishi.restspringproject.springrestservice.beans.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
