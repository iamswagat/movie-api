/**
 * 
 */
package com.stackroute.fse.capsule.moviecruiserservice.service;

import java.util.List;
import java.util.Optional;

import com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto;
import com.stackroute.fse.capsule.moviecruiserservice.exception.MovieAlreadyExistException;
import com.stackroute.fse.capsule.moviecruiserservice.exception.MovieNotFoundException;
import com.stackroute.fse.capsule.moviecruiserservice.repository.MovieRepository;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Swagat Srichandan
 *
 */

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private Mapper dozerMapper;

	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.service.MovieService#saveMovie(com.stackroute.fse.capsule.moviecruiserservice.domain.Movie)
	 */
	@Override
	public boolean saveMovie(final MovieDto movie) throws MovieAlreadyExistException {
		Optional<MovieDto> object = movieRepository.findByIdAndUserId(movie.getId(), movie.getUserId());
		if(object.isPresent()) {
			throw new MovieAlreadyExistException();
		}
		movieRepository.save(movie);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.service.MovieService#updateMovie(java.lang.Integer, com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto)
	 */
	@Override
	public MovieDto updateMovie(final Integer id, final MovieDto movie) throws MovieNotFoundException {
		MovieDto dbObject = movieRepository.findByIdAndUserId(movie.getId(), movie.getUserId()).orElseThrow(MovieNotFoundException::new);
		dozerMapper.map(movie, dbObject);
		return movieRepository.save(dbObject);
	}

	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.service.MovieService#deleteMovieById(java.lang.Integer)
	 */
	@Override
	public boolean deleteMovieById(final Integer id, final String userId) throws MovieNotFoundException {
		MovieDto entity = movieRepository.findByIdAndUserId(id, userId).orElseThrow(MovieNotFoundException::new);
		movieRepository.delete(entity);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.service.MovieService#getMovieById(java.lang.Integer)
	 */
	@Override
	public MovieDto getMovieById(final Integer id, final String userId) throws MovieNotFoundException {
		return movieRepository.findByIdAndUserId(id, userId).orElseThrow(MovieNotFoundException::new);
	}

	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.service.MovieService#getAllMovies()
	 */
	@Override
	public List<MovieDto> getAllMovies(final String userId) {
		return movieRepository.findByUserId(userId);
	}

}
