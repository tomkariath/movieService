package com.thomas.movieReview.repository;

import org.springframework.data.repository.CrudRepository;

import com.thomas.movieReview.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
