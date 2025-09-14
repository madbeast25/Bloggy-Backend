package com.bloggy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bloggy.Entities.Photo;
import com.bloggy.Services.PhotoServices;

@RestController
@RequestMapping("/bloggy/photo")
public class PhotoControllers {
	
	@Autowired
	private PhotoServices service;
	
	
	@PostMapping("/upload/{id}")
	public ResponseEntity<String> uploadPhoto(@RequestParam MultipartFile file,@PathVariable int id){
		try {
			Photo pic=service.SavePhoto(file, id);
			
			return new ResponseEntity<>("Photo saved in DB!",HttpStatus.OK);
		}catch(Exception ex) {
			return new ResponseEntity<>("Error "+ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> downloadPic(@PathVariable int id){
		Photo photo=service.getPhoto(id);
		
		return ResponseEntity.ok().contentType(MediaType.valueOf(photo.getType())).body(photo.getData());
	}
	
	

}
