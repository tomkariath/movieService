package com.thomas.movieReview.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.thomas.movieReview.exception.MovieNotFoundException;
import com.thomas.movieReview.model.Movie;
import com.thomas.movieReview.model.User;
import com.thomas.movieReview.repository.MovieRepository;
import com.thomas.movieReview.repository.UserRepository;

@RestController
public class MovieController {

	@Autowired
	MovieRepository movieRepo;

	@Autowired
	UserRepository userRepo;

	//getMovies
	@GetMapping(path = "/movies")
	public List<Movie> getAllMovies() {
		List<Movie> moviesList = movieRepo.findAll();
		return moviesList;
	}

	//showMovies
	@GetMapping(path = "/showlist")
	public ModelAndView showMovies() {
		ModelAndView mav = new ModelAndView("showlist");
		List<Movie> moviesList = movieRepo.findAll();
		mav.addObject("movies", moviesList);
		mav.addObject("movie", new Movie());
		return mav;
	}

	//addMovies
	@PostMapping(path = "/showlist")
	public void addMovieView(@ModelAttribute("movie") @Valid @RequestBody Movie movie, BindingResult bindingResult) {
		movieRepo.save(movie);
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
	public void addMovie(@ModelAttribute("movie") @Valid @RequestBody Movie movie) {
		movieRepo.save(movie);
	}

	//upVoteMovie
	@RequestMapping(path = "/movies/{movieId}/upVote")
	public void upVote(@PathVariable Integer movieId) throws Exception {
		Optional<Movie> movie = movieRepo.findById(movieId);

		if (!movie.isPresent()) {
			throw new MovieNotFoundException("Movie with id: "+movieId +" doesn't exist");
		}

		movie.get().setGoodCount(movie.get().getGoodCount()+1);	

		movieRepo.save(movie.get());
	}

	//downVoteMovie
	@RequestMapping(path = "/movies/{movieId}/downVote")
	public void downVoteMovie(@PathVariable Integer movieId) throws Exception {
		Optional<Movie> movie = movieRepo.findById(movieId);

		if (!movie.isPresent()) {
			throw new MovieNotFoundException("Movie with id: "+movieId +" doesn't exist");
		}

		movie.get().setBadCount(movie.get().getBadCount()+1);
		movieRepo.save(movie.get());
	}
}
