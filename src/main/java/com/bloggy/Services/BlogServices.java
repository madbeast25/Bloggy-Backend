package com.bloggy.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bloggy.DTO.BlogRes;
import com.bloggy.Entities.Blog;
import com.bloggy.Entities.User;
import com.bloggy.Repositories.BlogRepo;
import com.bloggy.Repositories.UserRepo;

@Service
public class BlogServices {
	
	@Autowired
	private BlogRepo repo;
	@Autowired
	private UserRepo userRepo;

	public BlogRes postNewBlog(String username,Blog newBlog) {
		
			User user=userRepo.findByUsername(username)
					          .orElseThrow(()-> 
					              new UsernameNotFoundException("User not found 404!"));
			newBlog.setUser(user);
			repo.save(newBlog);
			
			BlogRes res=new BlogRes();
			
			res.setId(newBlog.getId());
			res.setTitle(newBlog.getTitle());
			res.setContent(newBlog.getContent());
			res.setPostedOn(newBlog.getPostedOn());
			res.setLikes(newBlog.getLikes());
			
			return res;
	
	}
	
	public List<BlogRes> getAllBlogs(){
		List<BlogRes> res=new ArrayList<>();
		List<Blog> blogs=repo.findAll();
		
		for(Blog blog:blogs) {
			BlogRes bl=new BlogRes();
			
			bl.setId(blog.getId());
			bl.setContent(blog.getContent());
			bl.setTitle(blog.getTitle());
			bl.setPostedOn(blog.getPostedOn());
			bl.setLikes(blog.getLikes());
			
			res.add(bl);
		}
		
		return res;
	}
	
	public List<BlogRes> getAllBlogsById(int id){
		User user=userRepo.findById(id)
				          .orElseThrow(()-> new UsernameNotFoundException("404 user not found"));
		List<Blog> blogs=repo.findByUser(user);
		List<BlogRes> res=new ArrayList<>();
		
		for(Blog blog:blogs) {
			BlogRes bl=new BlogRes();
			
			bl.setId(blog.getId());
			bl.setContent(blog.getContent());
			bl.setTitle(blog.getTitle());
			bl.setPostedOn(blog.getPostedOn());
			bl.setLikes(blog.getLikes());
			
			res.add(bl);
			
		}
		
		return res;
	}
	
	public List<BlogRes> generateFeed(int user_id){
		List<Blog> allBlogs=repo.findAll();
		User Current_User=userRepo.findById(user_id).get();
		
		Set<User> Following=Current_User.getFollowing();
		List<BlogRes> feeds=new ArrayList<>();
		
		for(User user:Following) {
			List<Blog> userPosts=user.getBlogs();
			
			for(Blog blog:userPosts) {
				BlogRes bl=new BlogRes();
				
				bl.setId(blog.getId());
				bl.setTitle(blog.getTitle());
				bl.setContent(blog.getContent());
				bl.setPostedOn(blog.getPostedOn());
				bl.setLikes(blog.getLikes());
				
				feeds.add(bl);
			}
		}
		
		return feeds;
	}
	
	public String deleteBlog(int blog_id) {
		try {
			repo.deleteById(blog_id);
			
			return "Blog removed successFully!";
		}catch (Exception e) {
		    return e.getMessage();
		}
	}
	
	public BlogRes likeBlog(int blog_id,int like) {
		try {
			Blog blog=repo.findById(blog_id).get();
			
			blog.setLikes(blog.getLikes()+like);
			repo.save(blog);
			
			BlogRes res=new BlogRes();
			
			res.setId(blog.getId());
			res.setTitle(blog.getTitle());
			res.setContent(blog.getContent());
			res.setPostedOn(blog.getPostedOn());
			res.setLikes(blog.getLikes());
			
			return res;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return null;
	}
}
