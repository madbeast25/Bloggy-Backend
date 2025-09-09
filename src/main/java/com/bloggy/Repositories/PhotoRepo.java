package com.bloggy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloggy.Entities.Photo;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Integer> {

}
