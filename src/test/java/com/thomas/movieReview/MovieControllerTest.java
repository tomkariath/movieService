package com.thomas.movieReview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.thomas.movieReview.model.Movie;
import com.thomas.movieReview.repository.MovieRepository;
import com.thomas.movieReview.repository.UserRepository;

public class MovieControllerTest extends ApiTest{

	/*
	 * @Autowired private WebApplicationContext context;
	 */

	/*
	 * @Autowired private Filter springSecurityFilterChain;
	 */
	
	@Mock
	MovieRepository movieRepo;
	
	@Mock
	UserRepository userRepo;

	@Before
	@Override
	public void initialize() {
		super.initialize();
		 mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
		            .addFilters(springSecurityFilterChain).build();
	}

	@Test
	//@WithMockUser(roles="ADMIN")
	public void getMoviesTest() throws Exception {
		String uri = "/movies";
		MvcResult mvcResult = mockMvc.perform(get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE).with(httpBasic(USER_NAME, USER_PASS))).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String response = mvcResult.getResponse().getContentAsString();
		Movie[] movieList = super.jsonToObj(response, Movie[].class);
		assertTrue(movieList.length > 0);
	}
	
	@Test
	//@WithMockUser(roles="USER")
	public void addMovieUserTest() throws Exception {
		String uri = "/movies";
		
		Movie movie = new Movie();
		movie.setId(19);
		movie.setName("Good Will Hunting");
		movie.setGoodCount(0);
		movie.setBadCount(0);
		String json = super.objToJson(movie);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json).with(httpBasic(ADMIN_NAME, ADMIN_PASS))).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}
	
	@Test
	@WithMockUser(roles="ADMIN")
	public void addMovieAdminTest() throws Exception {
		String uri = "/movies";
		
		Movie movie = new Movie();
		movie.setId(19);
		movie.setName("Good Will Hunting");
		movie.setGoodCount(0);
		movie.setBadCount(0);
		String json = super.objToJson(movie);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json).with(httpBasic(USER_NAME, USER_PASS))).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(403, status);
	}

	@Test
	//@WithMockUser(roles="ADMIN")
	public void upVoteTest() throws Exception {
		int id=19;
		Optional<Movie> mockMovie = movieRepo.findById(id);
		String uri = "/movies/"+id+"/upVote";


		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).with(httpBasic(USER_NAME, USER_PASS)))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();

		if (mockMovie.isPresent()) {
			assertEquals(200, status);

			String response = mvcResult.getResponse().getContentAsString();

			Movie movie = super.jsonToObj(response, Movie.class);
			assertEquals(Long.valueOf(movie.getId()), Long.valueOf(id));
			assertEquals(Long.valueOf(movie.getGoodCount()), 
					Long.valueOf(mockMovie.get().getGoodCount()+1));
			assertEquals(Long.valueOf(movie.getBadCount()), 
					Long.valueOf(mockMovie.get().getBadCount()));
		}
		else {
			assertEquals(404, status);
		}
	}

	@Test
	//@WithMockUser(roles="ADMIN")
	public void downVoteTest() throws Exception {
		int id=19;
		Optional<Movie> mockMovie = movieRepo.findById(id);
		String uri = "/movies/"+id+"/upVote";


		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).with(httpBasic(USER_NAME, USER_PASS)))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();

		if (mockMovie.isPresent()) {
			assertEquals(200, status);

			String response = mvcResult.getResponse().getContentAsString();

			Movie movie = super.jsonToObj(response, Movie.class);
			assertEquals(Long.valueOf(movie.getId()), Long.valueOf(id));
			assertEquals(Long.valueOf(movie.getGoodCount()), 
					Long.valueOf(mockMovie.get().getGoodCount()));
			assertEquals(Long.valueOf(movie.getBadCount()), 
					Long.valueOf(mockMovie.get().getBadCount()+1));
		}
		else {
			assertEquals(404, status);
		}
	}
}
