package com.bloggy.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloggy.Entities.Blog;
import com.bloggy.Entities.User;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {

	public List<Blog> findByUser(User user);
}
