package com.thomas.movieReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomas.movieReview.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

}