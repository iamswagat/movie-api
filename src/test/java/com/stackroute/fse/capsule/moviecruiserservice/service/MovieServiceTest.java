package com.stackroute.fse.capsule.moviecruiserservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto;
import com.stackroute.fse.capsule.moviecruiserservice.exception.MovieAlreadyExistException;
import com.stackroute.fse.capsule.moviecruiserservice.exception.MovieNotFoundException;
import com.stackroute.fse.capsule.moviecruiserservice.repository.MovieRepository;
import com.stackroute.fse.capsule.moviecruiserservice.service.MovieService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieServiceTest {
	
	@MockBean
	private MovieRepository mockRepository;
	
	@Autowired
	private MovieService testMovieService;
	
	private MovieDto testFixture;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();;
	
	@Before
	public void setup() {
		testFixture = new MovieDto(1, "Dark Knight", "Christopher Nolan's Batman Trilogy", "https://www.imdb.com/title/tt0468569/mediaviewer/rm4023877632", "07/18/2008", "9.0", "1,989,145", "1");
	}
	
	@Test
	public void testSaveMovie_failure() {
		when(mockRepository.findByIdAndUserId(this.testFixture.getId(), this.testFixture.getUserId())).thenReturn(Optional.of(this.testFixture));
		expectedException.expect(MovieAlreadyExistException.class);
		testMovieService.saveMovie(testFixture);
	}
	
	@Test
	public void testSaveMovie_success() {
		when(mockRepository.findByIdAndUserId(this.testFixture.getId(), this.testFixture.getUserId())).thenReturn(Optional.empty());
		when(mockRepository.save(this.testFixture)).thenReturn(this.testFixture);
		boolean actual = testMovieService.saveMovie(testFixture);
		assertThat(actual).isTrue();
	}
	
	@Test
	public void testUpdateMovie() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		when(mockRepository.findByIdAndUserId(this.testFixture.getId(), this.testFixture.getUserId())).thenReturn(Optional.of(this.testFixture));
		MovieDto updatedEntity = (MovieDto) BeanUtils.cloneBean(testFixture);
		updatedEntity.setComments("Christopher Nolan's Batman Trilogy, Introducing Joker");
		when(mockRepository.save(any(MovieDto.class))).thenReturn(updatedEntity);
		assertThat(this.testMovieService.updateMovie(updatedEntity.getId(), updatedEntity)).hasFieldOrPropertyWithValue("comments", updatedEntity.getComments());
	}
	
	@Test
	public void testDeleteMovieById_failure() {
		when(mockRepository.findByIdAndUserId(this.testFixture.getId(), this.testFixture.getUserId())).thenReturn(Optional.empty());
		expectedException.expect(MovieNotFoundException.class);
		this.testMovieService.deleteMovieById(this.testFixture.getId(), this.testFixture.getUserId());
	}
	
	@Test
	public void testDeleteMovieById_success() {
		when(mockRepository.findByIdAndUserId(this.testFixture.getId(), this.testFixture.getUserId())).thenReturn(Optional.of(this.testFixture));
		assertThat(this.testMovieService.deleteMovieById(this.testFixture.getId(), this.testFixture.getUserId())).isTrue();
	}
	
	@Test
	public void testGetMovieById_failure() {
		when(mockRepository.findByIdAndUserId(this.testFixture.getId(), this.testFixture.getUserId())).thenReturn(Optional.empty());
		expectedException.expect(MovieNotFoundException.class);
		this.testMovieService.getMovieById(this.testFixture.getId(), this.testFixture.getUserId());
	}
	
	@Test
	public void testGetMovieById_success() {
		when(mockRepository.findByIdAndUserId(this.testFixture.getId(), this.testFixture.getUserId())).thenReturn(Optional.of(this.testFixture));
		assertThat(testMovieService.getMovieById(this.testFixture.getId(), this.testFixture.getUserId())).isEqualToComparingFieldByField(testFixture);
	}
	
	@Test
	public void testGetAllMovies() {
		when(mockRepository.findByUserId(this.testFixture.getUserId())).thenReturn(Collections.emptyList());
		assertThat(testMovieService.getAllMovies(this.testFixture.getUserId())).asList().hasOnlyElementsOfType(MovieDto.class);
	}
}
