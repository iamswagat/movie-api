package com.stackroute.fse.capsule.moviecruiserservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "movie" )
public class MovieDto {

    /**
     * id for movie
     */
    @Id
    @Column( name = "id")
    private int id;

    /**
     * name of the movie
     */ 
    @Column( name = "name")
    private String name;

    /**
     * field for comments of movie
     */
    @Column( name = "comments")
    private String comments;

    /**
     * field for poster path for movie
     */
    @Column( name = "poster_path")
    private String posterPath;

    /**
     * field for release date of movie
     */
    @Column( name = "release_date")
    private String releaseDate;

    /**
     * field for vote average of movie
     */
    @Column( name = "vote_average")
    private String voteAverage;

    /**
     * field for vote count of movie
     */
    @Column( name = "vote_count")
    private String voteCount;

    @Column( name = "user_id")
    private String userId;
}