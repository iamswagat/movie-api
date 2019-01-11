package com.stackroute.fse.capsule.moviecruiserservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.fse.capsule.moviecruiserservice.delegate.MovieDelegator;
import com.stackroute.fse.capsule.moviecruiserservice.resource.Movie;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieCruiserApiTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private MovieDelegator mockDelegator;
	
	@Autowired
	ObjectMapper objectMapper;

	Movie testFixture;
	
	String jwtToken;

    @Before
    public void setup() {
        testFixture = new Movie();
		testFixture.setId(1);
		jwtToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtb3ZpZS1jcnVpc2VyLWF1dGgiLCJzdWIiOiI3IiwibmFtZSI6IlN3YWdhdCBTcmljaGFuZGFuIiwiaWF0IjoxNTQ1NzYyNjAwLCJleHAiOjE1NzcyOTg2MDB9.MlnGhC06kJQ62qB9iTAUx7MTkyThvvgYXXb9Gue7Bxa9RmZitjeiRKIRcUSydZwQeKe9eA_o85zSBCTslTdikg";
    }

    @Test
    public void saveNewMovieTest_201_response() throws Exception {
    	when(this.mockDelegator.addMovieToWatchlist(any(Movie.class), anyString())).thenReturn(true);
        this.mockMvc.perform(post("/api/v1/movies")
        		.header("authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(testFixture)))
            .andExpect(status().isCreated())
        	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        	.andExpect(jsonPath("$.message").value("Movie added successfully"));
        verify(this.mockDelegator, times(1)).addMovieToWatchlist(any(Movie.class), anyString());
        verifyNoMoreInteractions(this.mockDelegator);
    }

    @Test
    public void saveNewMovieTest_415_response() throws Exception {
        this.mockMvc.perform(post("/api/v1/movies")
				.header("authorization", jwtToken)
                .contentType(MediaType.APPLICATION_XML)
                .content(this.objectMapper.writeValueAsBytes(new Movie())))
            .andExpect(status().isUnsupportedMediaType());
        verifyZeroInteractions(this.mockDelegator);
    }

    @Test
    public void saveNewMovieTest_400_response() throws Exception {
        this.mockMvc.perform(post("/api/v1/movies")
				.header("authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pid\": 1231}"))
            .andExpect(status().isBadRequest());
        verifyZeroInteractions(this.mockDelegator);
    }
    
    @Test
    public void saveNewMovieTest_409_response() throws Exception {
    	when(this.mockDelegator.addMovieToWatchlist(any(Movie.class), anyString())).thenReturn(false);
        this.mockMvc.perform(post("/api/v1/movies")
				.header("authorization", jwtToken)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(this.objectMapper.writeValueAsBytes(this.testFixture)))
    		.andExpect(status().isConflict())
        	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        	.andExpect(jsonPath("$.message").value("Movie already exists"));
        verify(this.mockDelegator, times(1)).addMovieToWatchlist(any(Movie.class), anyString());
        verifyNoMoreInteractions(this.mockDelegator);
    }
    
    @Test
    public void updateMovieInformation_200_response() throws Exception {
    	when(this.mockDelegator.updateMovieInfo(anyInt(), any(Movie.class), anyString())).thenReturn(testFixture);
    	this.mockMvc.perform(put("/api/v1/movies/{id}", 1)
				.header("authorization", jwtToken)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(this.objectMapper.writeValueAsBytes(this.testFixture)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andExpect(jsonPath("$.id").value(1));
    	verify(this.mockDelegator, times(1)).updateMovieInfo(anyInt(), any(Movie.class), anyString());
    	verifyNoMoreInteractions(this.mockDelegator);
    }
    
    @Test
    public void deleteMovieFromWatchlist_200_response() throws Exception {
    	when(this.mockDelegator.deleteMovieFromWatchlist(anyInt(), anyString())).thenReturn(true);
    	this.mockMvc.perform(delete("/api/v1/movies/{id}", 1)
				.header("authorization", jwtToken)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(this.objectMapper.writeValueAsBytes(this.testFixture)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    	.andExpect(jsonPath("$.message").value("Movie deleted successfully"));
    	verify(this.mockDelegator, times(1)).deleteMovieFromWatchlist(anyInt(), anyString());
    	verifyNoMoreInteractions(this.mockDelegator);
    }
    
    @Test
    public void getAllMoviesFromWatchlist_200_response() throws Exception {
    	when(this.mockDelegator.fetchAllMovies(anyString())).thenReturn(Arrays.asList(this.testFixture));
    	this.mockMvc.perform(get("/api/v1/movies")
				.header("authorization", jwtToken)
    			.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(content().string(containsString(this.objectMapper.writeValueAsString(Arrays.asList(this.testFixture)))));
    	verify(this.mockDelegator, times(1)).fetchAllMovies(anyString());
    	verifyNoMoreInteractions(this.mockDelegator);
    }
    
    @Test
    public void getMovieById_200_response() throws Exception {
    	when(this.mockDelegator.fetchMovieFromWatchlist(anyInt(), anyString())).thenReturn(this.testFixture);
    	this.mockMvc.perform(get("/api/v1/movies/{id}", 1)
				.header("authorization", jwtToken)
    			.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(content().string(containsString(this.objectMapper.writeValueAsString(this.testFixture))));
    	verify(this.mockDelegator, times(1)).fetchMovieFromWatchlist(anyInt(), anyString());
    	verifyNoMoreInteractions(this.mockDelegator);
    }
}