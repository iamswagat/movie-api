package com.stackroute.fse.capsule.moviecruiserservice.delegate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto;
import com.stackroute.fse.capsule.moviecruiserservice.resource.Movie;
import com.stackroute.fse.capsule.moviecruiserservice.service.MovieService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieDelegatorTest {
	
	@Autowired
	private MovieDelegator testDelegator;
	
	@MockBean
	private MovieService mockService;
	
	private Movie testFixture;
	
	private MovieDto testEntity;
	
	@Before
	public void setup() {
		testFixture = new Movie(1, "Dark Knight", "Christopher Nolan's Batman Trilogy", "http://www.imdb.com/title/tt0468569/mediaviewer/rm4023877632", "07/18/2008", "9.0", "1,989,145", "1");
		testEntity = new MovieDto(1, "Dark Knight", "Christopher Nolan's Batman Trilogy", "http://www.imdb.com/title/tt0468569/mediaviewer/rm4023877632", "07/18/2008", "9.0", "1,989,145", "1");
	}
	
	@Test
	public void addMovieToWatchlistTest() {
		when(this.mockService.saveMovie(any(MovieDto.class))).thenReturn(true);
		assertThat(this.testDelegator.addMovieToWatchlist(this.testFixture, this.testFixture.getUserId())).isTrue();
	}

	@Test
	public void updateMovieInfoTest() {
		when(this.mockService.updateMovie(anyInt(), any(MovieDto.class))).thenReturn(testEntity);
		assertThat(this.testDelegator.updateMovieInfo(1, this.testFixture, this.testFixture.getUserId())).isEqualToComparingFieldByField(testFixture).isInstanceOf(Movie.class);
	}
	
	@Test
	public void deleteMovieFromWatchlistTest() {
		when(this.mockService.deleteMovieById(this.testEntity.getId(), this.testEntity.getUserId())).thenReturn(true);
		assertThat(this.testDelegator.deleteMovieFromWatchlist(this.testFixture.getId(), this.testFixture.getUserId())).isTrue();
	}
	
	@Test
	public void fetchMovieFromWatchlistTest() {
		when(this.mockService.getMovieById(this.testEntity.getId(), this.testEntity.getUserId())).thenReturn(testEntity);
		assertThat(this.testDelegator.fetchMovieFromWatchlist(testFixture.getId(), testFixture.getUserId())).isEqualToComparingFieldByField(testFixture).isInstanceOf(Movie.class);
	}
	
	@Test
	public void fetchAllMoviesTest() {
		when(this.mockService.getAllMovies(this.testEntity.getUserId())).thenReturn(Arrays.asList(testEntity));
		assertThat(this.testDelegator.fetchAllMovies(this.testEntity.getUserId())).hasOnlyElementsOfType(Movie.class).usingFieldByFieldElementComparator().contains(testFixture);
	}
}
