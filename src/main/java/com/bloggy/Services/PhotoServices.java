package com.bloggy.Services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bloggy.Entities.Photo;
import com.bloggy.Entities.User;
import com.bloggy.Repositories.PhotoRepo;
import com.bloggy.Repositories.UserRepo;

@Service
public class PhotoServices {
	
	@Autowired
	private PhotoRepo repo;
	@Autowired
	private UserRepo userRepo;

	public Photo SavePhoto(MultipartFile file,int id) throws IOException {
		Photo photo;
		User user=userRepo.findById(id).get();
		
		if(user.getProfilePic() == null) {
			photo=new Photo();
			photo.setFilename(file.getOriginalFilename());
			photo.setType(file.getContentType());
			photo.setData(file.getBytes());
			photo.setPhoto_user(user);
		}else {
			photo=user.getProfilePic();
			
			photo.setType(file.getContentType());
			photo.setData(file.getBytes());
		}
		
		Photo saved=repo.save(photo);
		
		user.setProfilePic(saved);
		userRepo.save(user);
		
		return saved;
	}
	
	public Photo getPhoto(int id) {
		User user=userRepo.findById(id).get();
		Photo photo=user.getProfilePic();
		
		return photo;
	}
	
	
}
