package com.stackroute.fse.capsule.moviecruiserservice.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
	
	private int id;
	
	@JsonProperty("title")
	private String name;
	
	private String comments;
	
	@JsonProperty("poster_path")
	private String posterPath;
	
	@JsonProperty("release_date")
	private String releaseDate;
	
	@JsonProperty("vote_average")
	private String voteAverage;
	
	@JsonProperty("vote_count")
	private String voteCount;

	@JsonIgnore
	private String userId;
}
