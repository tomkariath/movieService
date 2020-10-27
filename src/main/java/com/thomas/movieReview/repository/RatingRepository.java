package com.thomas.movieReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomas.movieReview.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	
	@SuppressWarnings("unchecked")
	@Override
	Rating save (Rating rating);
	
	Rating findByMovieIdAndUserId(Integer movieId, Integer userId);
}
