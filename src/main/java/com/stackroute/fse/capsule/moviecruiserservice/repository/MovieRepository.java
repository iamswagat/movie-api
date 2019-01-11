package com.stackroute.fse.capsule.moviecruiserservice.repository;

import java.util.List;
import java.util.Optional;

import com.stackroute.fse.capsule.moviecruiserservice.domain.MovieDto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieDto, Integer> {
    
    Optional<MovieDto> findByIdAndUserId(int id, String userId);

    List<MovieDto> findByUserId(String userId);
}