package com.thomas.movieReview;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MovieReviewApplication.class)
@WebAppConfiguration
public abstract class ApiTest {

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;


	void initialize() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected String objToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	protected <T> T jsonToObj(String json, Class<T> genClass) throws JsonMappingException, 
		JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, genClass);
	}

}
