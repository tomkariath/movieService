package com.thomas.movieReview.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thomas.movieReview.exceptions.MovieNotFoundException;
import com.thomas.movieReview.models.Movie;
import com.thomas.movieReview.repository.MovieRepository;

@RestController
public class MovieController {
	
	@Autowired
	MovieRepository movieRepo;
	
	//getMovies
	@GetMapping(path = "/movies")
	public List<Movie> getAllMovies() {
		List<Movie> moviesList = movieRepo.findAll();
		return moviesList;
	}
	
	//addMovies
	@PostMapping(path = "/movies")
	public Movie addMovie(@Valid @RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	//upVoteMovie
	@PutMapping(path = "/movies/{movieId}/upVote")
	public Optional<Movie> upVote(@PathVariable Integer movieId) throws Exception {
		Optional<Movie> movie = movieRepo.findById(movieId);
		
		if (!movie.isPresent()) {
			 throw new MovieNotFoundException("Movie with id: "+movieId +" doesn't exist");
		}
		
		movie.get().setGoodCount(movie.get().getGoodCount()+1);	
		
		movieRepo.save(movie.get());
		return movie;
	}
	
	//downVoteMovie
	@PutMapping(path = "/movies/{movieId}/downVote")
	public Optional<Movie> downVoteMovie(@PathVariable Integer movieId) throws Exception {
		Optional<Movie> movie = movieRepo.findById(movieId);
		
		if (!movie.isPresent()) {
			 throw new MovieNotFoundException("Movie with id: "+movieId +" doesn't exist");
		}
		
		movie.get().setBadCount(movie.get().getBadCount()+1);
		movieRepo.save(movie.get());
		
		return movie;
	}
}
