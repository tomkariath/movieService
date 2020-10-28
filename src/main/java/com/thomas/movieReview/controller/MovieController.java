package com.thomas.movieReview.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.thomas.movieReview.exception.MovieNotFoundException;
import com.thomas.movieReview.model.Movie;
import com.thomas.movieReview.model.Rating;
import com.thomas.movieReview.model.User;
import com.thomas.movieReview.repository.MovieRepository;
import com.thomas.movieReview.repository.RatingRepository;
import com.thomas.movieReview.repository.UserRepository;

@RestController
public class MovieController {

	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	MovieRepository movieRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	RatingRepository ratingRepo;

	//getMovies
	@GetMapping(path = "/movies")
	public List<Movie> getAllMovies() {
		logger.info("GET /movies");
		List<Movie> moviesList = movieRepo.findAll();
		return moviesList;
	}

	//showMovies
	@GetMapping(path = "/showlist")
	public ModelAndView showMovies() {
		logger.info("GET /showlist");
		ModelAndView mav = new ModelAndView("showlist");
		List<Movie> moviesList = movieRepo.findAll();
		mav.addObject("movies", moviesList);
		return mav;
	}

	//users
	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		List<User> userList = userRepo.findAll();
		return userList;
	}

	//addMovies
	@PostMapping(path = "/movies")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void addMovie(@Valid @RequestBody Movie movie, 
			HttpServletResponse httpResponse) throws IOException {
		logger.info("POST /movies "+movie.toString());
		movieRepo.save(movie);
	}

	//upVoteMovie
	@RequestMapping(path = "/movies/{movieId}/upvote")
	public void upVote(@PathVariable Integer movieId) throws Exception {
		logger.info("GET /movies/"+movieId+"upvote");

		Optional<Movie> movie = movieRepo.findById(movieId);

		if (!movie.isPresent()) {
			throw new MovieNotFoundException("Movie with id: "+movieId +" doesn't exist");
		}

		User reviewer = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (ratingRepo.findByMovieIdAndUserId(movieId, reviewer.getId())==null) {
			Rating rating = new Rating();
			rating.setMovie(movie.get());
			rating.setUser(reviewer);

			ratingRepo.save(rating);

			movie.get().setGoodCount(movie.get().getGoodCount()+1);	
			movieRepo.save(movie.get());
		}
		else {
			throw new Exception("Already Voted");
		}
	}

	//downVoteMovie
	@RequestMapping(path = "/movies/{movieId}/downvote")
	public void downVoteMovie(@PathVariable Integer movieId) throws Exception {
		logger.info("GET /movies/"+movieId+"upvote");
		Optional<Movie> movie = movieRepo.findById(movieId);

		if (!movie.isPresent()) {
			throw new MovieNotFoundException("Movie with id: "+movieId +" doesn't exist");
		}

		User reviewer = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (ratingRepo.findByMovieIdAndUserId(movieId, reviewer.getId())==null) {
			Rating rating = new Rating();
			rating.setMovie(movie.get());
			rating.setUser(reviewer);

			ratingRepo.save(rating);

			movie.get().setBadCount(movie.get().getBadCount()+1);	
			movieRepo.save(movie.get());
		}
		else {
			throw new Exception("Already Voted");
		}
	}
}
