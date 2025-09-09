package com.bloggy.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggy.DTO.BlogRes;
import com.bloggy.Entities.Blog;
import com.bloggy.Services.BlogServices;

@RestController
@RequestMapping("/bloggy/blog")
@CrossOrigin(
  origins = "http://localhost:5173"
)
public class BlogControllers {
	
	@Autowired
	private BlogServices service;

	@PostMapping("/post")
	public ResponseEntity<BlogRes> postBlog(@RequestBody Blog newBlog,Principal principal){
		String username=principal.getName();
		BlogRes res=service.postNewBlog(username, newBlog);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/getAllBlogs")
	public ResponseEntity<List<BlogRes>> getAllPosts(){
		List<BlogRes> blogs=service.getAllBlogs();
		
		return new ResponseEntity<>(blogs,HttpStatus.OK);
	}
	
	@GetMapping("/getBlogs/{id}")
	public ResponseEntity<List<BlogRes>> getBlogs(@PathVariable int id){
		List<BlogRes> blogs=service.getAllBlogsById(id);
		
		return new ResponseEntity<>(blogs,HttpStatus.OK);
	}
	
	@GetMapping("/feeds/{id}")
	public ResponseEntity<List<BlogRes>> generateFeed(@PathVariable int id){
		List<BlogRes> feeds=service.generateFeed(id);
		
		return new ResponseEntity<>(feeds,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBlog/{blog_id}")
	public ResponseEntity<String> deleteBlog(@PathVariable int blog_id){
		String res=service.deleteBlog(blog_id);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PutMapping("/addLike/{blog_id}/{likes}")
	public ResponseEntity<BlogRes> likeBlog(@PathVariable int blog_id,@PathVariable int likes){
		BlogRes res=service.likeBlog(blog_id, likes);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
