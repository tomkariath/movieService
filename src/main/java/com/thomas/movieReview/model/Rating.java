package com.thomas.movieReview.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ratingId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Movie movie;


	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Override
	public String toString() {
		return "Rating [ratingId=" + ratingId + ", movie=" + movie + ", user=" + user + "]";
	}
	
	public Integer getRatingId() {
		return ratingId;
	}

	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
