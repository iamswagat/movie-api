package com.stackroute.fse.capsule.moviecruiserservice.delegate;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto;
import com.stackroute.fse.capsule.moviecruiserservice.resource.Movie;
import com.stackroute.fse.capsule.moviecruiserservice.service.MovieService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MovieDelegatorImpl implements MovieDelegator {
	
	private Mapper dozerMapper;
	
	private MovieService movieService;

	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.delegate.MovieDelegator#addMovieToWatchlist(com.stackroute.fse.capsule.moviecruiserservice.resource.Movie, java.lang.String)
	 */
	@Override
	public boolean addMovieToWatchlist(final Movie movie, final String userId) {
		MovieDto entity = dozerMapper.map(movie, MovieDto.class);
		entity.setUserId(userId);
		return movieService.saveMovie(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.delegate.MovieDelegator#updateMovieInfo(java.lang.Integer, com.stackroute.fse.capsule.moviecruiserservice.resource.Movie, java.lang.String)
	 */
	@Override
	public Movie updateMovieInfo(final Integer id, final Movie movie, final String userId) {
		MovieDto dto = dozerMapper.map(movie, MovieDto.class);
		dto.setUserId(userId);
		MovieDto entity = movieService.updateMovie(id, dto);
		return dozerMapper.map(entity, Movie.class);
	}
	
	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.delegate.MovieDelegator#deleteMovieFromWatchlist(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean deleteMovieFromWatchlist(final Integer id, final String userId) {
		return movieService.deleteMovieById(id, userId);
	}
	
	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.delegate.MovieDelegator#fetchMovieFromWatchlist(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Movie fetchMovieFromWatchlist(final Integer id, final String userId) {
		MovieDto entity = movieService.getMovieById(id, userId);
		return dozerMapper.map(entity, Movie.class);
	}
	
	/* (non-Javadoc)
	 * @see com.stackroute.fse.capsule.moviecruiserservice.delegate.MovieDelegator#fetchAllMovies(java.lang.String)
	 */
	@Override
	public List<Movie> fetchAllMovies(final String userId) {
		return movieService.getAllMovies(userId)
				.stream().map(source -> dozerMapper.map(source, Movie.class)).collect(Collectors.toList());
	}

}
