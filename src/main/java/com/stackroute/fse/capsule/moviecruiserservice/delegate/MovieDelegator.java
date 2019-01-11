package com.stackroute.fse.capsule.moviecruiserservice.delegate;

import java.util.List;

import com.stackroute.fse.capsule.moviecruiserservice.resource.Movie;

public interface MovieDelegator {

	/**
	 * Add movie to {@code userId}'s watchlist
	 * 
	 * @param movie
	 * @param userId
	 * @return true/false
	 */
	boolean addMovieToWatchlist(Movie movie, String userId);

	/**
	 * Update Movie Comment to {@code userId}'s watchlist
	 * 
	 * @param id
	 * @param movie
	 * @param userId
	 * @return Updated Movie
	 */
	Movie updateMovieInfo(Integer id, Movie movie, String userId);

	/**
	 * Delete Movie from {@code userId}'s watchlist
	 * 
	 * @param id
	 * @param userId
	 * @return true/false
	 */
	boolean deleteMovieFromWatchlist(Integer id, String userId);

	/**
	 * Get Movie from {@code userId}'s watchlist
	 * 
	 * @param id
	 * @param userId
	 * @return Movie
	 */
	Movie fetchMovieFromWatchlist(Integer id, String userId);

	/**
	 * Get all Movies from {@code userId}'s watchlist
	 * 
	 * @param userId
	 * @return List of movies
	 */
	List<Movie> fetchAllMovies(String userId);

}