package com.stackroute.fse.capsule.moviecruiserservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
public class MovieRepositoryTest {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Test
	public void testSaveMovieEntity() {
		MovieDto entity = new MovieDto(1, "Dark Knight", "Christopher Nolan's Batman Trilogy", null, null, null, null, null);
		movieRepository.save(entity);
		MovieDto object = movieRepository.findById(1).get();
		assertThat(object).isEqualToComparingFieldByField(entity);
	}

	@Test
	public void testDeleteMovieEntity() {
		MovieDto entity = new MovieDto(1, "Dark Knight", "Christopher Nolan's Batman Trilogy", null, null, null, null, null);
		movieRepository.save(entity);
		MovieDto object = movieRepository.findById(1).get();
		assertThat(object).isNotNull();
		movieRepository.delete(object);
		assertThat(movieRepository.findById(1)).isEmpty();
	}
	
	@Test
	public void testFindByIdAndUserId() {
		MovieDto entity = new MovieDto(1, "Dark Knight", "Christopher Nolan's Batman Trilogy", null, null, null, null, "1");
		movieRepository.save(entity);
		Optional<MovieDto> object = movieRepository.findByIdAndUserId(entity.getId(), entity.getUserId());
		assertThat(object).isNotEmpty().usingFieldByFieldValueComparator().contains(entity);
	}
	
	@Test
	public void testFindByUserId() {
		MovieDto entity1 = new MovieDto(1, "Dark Knight", "Christopher Nolan's Batman Trilogy", null, null, null, null, "1");
		MovieDto entity2 = new MovieDto(2, "Dark Knight Returns", "Christopher Nolan's Batman Trilogy", null, null, null, null, "1");
		movieRepository.save(entity1);
		movieRepository.save(entity2);
		List<MovieDto> object = movieRepository.findByUserId("1");
		assertThat(object).hasSize(2);
	}
}
