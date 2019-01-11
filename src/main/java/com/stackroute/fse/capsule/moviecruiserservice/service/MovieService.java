package com.stackroute.fse.capsule.moviecruiserservice.service;

import java.util.List;

import com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto;
import com.stackroute.fse.capsule.moviecruiserservice.exception.MovieAlreadyExistException;
import com.stackroute.fse.capsule.moviecruiserservice.exception.MovieNotFoundException;

public interface MovieService {

    /**
     * method declaration for save movie
     * 
     * @param movie
     * @return boolean
     * @throws MovieAlreadyExistException
     */
    boolean saveMovie(MovieDto movie) throws MovieAlreadyExistException;

    /**
     * method declaration for updating movie
     * 
     * @param movie
     * @param movie ID
     * @return Movie
     * @throws MovieNotFoundException
     */
    MovieDto updateMovie(Integer id, MovieDto movie) throws MovieNotFoundException;

    /**
     * method declaration for deleting a movie
     * 
     * @param id
     * @return boolean
     * @throws MovieNotFoundException
     */
    boolean deleteMovieById(Integer id, String userId) throws MovieNotFoundException;

    /**
     * method declaration for getting any movie by its id
     * 
     * @param id
     * @return Movie
     * @throws MovieNotFoundException
     */
    MovieDto getMovieById(Integer id, String userId) throws MovieNotFoundException;

    /**
     * method declaration for retrieving all the movies from database
     * 
     * @return List<Movie>
     */
    List<MovieDto> getAllMovies(String userId);

}