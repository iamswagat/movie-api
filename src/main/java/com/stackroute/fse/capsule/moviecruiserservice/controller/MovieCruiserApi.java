package com.stackroute.fse.capsule.moviecruiserservice.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stackroute.fse.capsule.moviecruiserservice.delegate.MovieDelegator;
import com.stackroute.fse.capsule.moviecruiserservice.exception.MovieAlreadyExistException;
import com.stackroute.fse.capsule.moviecruiserservice.resource.Movie;
import com.stackroute.fse.capsule.moviecruiserservice.resource.MovieResourceValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import springfox.documentation.annotations.ApiIgnore;

@Api
@CrossOrigin
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
public class MovieCruiserApi {

    private MovieDelegator delegate;

    private MovieResourceValidator validator;

    @InitBinder
    public void dataBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @PostMapping
    @ApiOperation(value = "Add Movie to Watchlist")
    @ApiResponses({ @ApiResponse(code = 201, message = "Movie added successfully"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Movie already exists") })
    public ResponseEntity<?> saveNewMovie(
            @ApiParam(value = "Movie Request", required = true) @Validated @RequestBody final Movie movie,
            HttpServletRequest request, HttpServletResponse response) {

        final String userId = extracted(request);

        return Optional.of(movie).filter(mov -> delegate.addMovieToWatchlist(mov, userId))
                .map(obj -> ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri())
                        .body(new ApiResponseMessage("Movie added successfully")))
                .orElseThrow(MovieAlreadyExistException::new);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Comment for the Movie")
    @ApiResponses({ @ApiResponse(code = 200, message = "Movie updated successfully"),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Movie not found") })
    public ResponseEntity<?> updateMovieInformation(
            @ApiParam(value = "Movie Id", required = true) @PathVariable final int id,
            @ApiParam(value = "Movie Request", required = true) @Validated @RequestBody final Movie movie,
            HttpServletRequest request, HttpServletResponse response) {
        
        final String userId = extracted(request);
        return ResponseEntity.ok().body(delegate.updateMovieInfo(id, movie, userId));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a movie from watchlist")
    @ApiResponses({ @ApiResponse(code = 200, message = "Movie deleted successfully"),
            @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Movie not found") })
    public ResponseEntity<?> deleteMovieFromWatchlist(
            @ApiParam(value = "Movie Id", required = true) @PathVariable final int id,
            HttpServletRequest request, HttpServletResponse response) {
        
        final String userId = extracted(request);
        delegate.deleteMovieFromWatchlist(id, userId);
        return ResponseEntity.ok().body(new ApiResponseMessage("Movie deleted successfully"));
    }

    @GetMapping
    @ApiOperation(value = "Fetch all movies from watchlist")
    @ApiResponses({ @ApiResponse(code = 200, message = "Movie fetched successfully") })
    public ResponseEntity<?> getAllMoviesFromWatchlist(HttpServletRequest request, HttpServletResponse response) {
        final String userId = extracted(request);
        return ResponseEntity.ok().body(delegate.fetchAllMovies(userId));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Fetch movie by id from watchlist")
    @ApiResponses({ @ApiResponse(code = 200, message = "Movie fetched successfully"),
            @ApiResponse(code = 404, message = "Movie not found") })
    public ResponseEntity<?> getMovieById(@ApiParam(value = "Movie Id", required = true) @PathVariable final int id,
            HttpServletRequest request, HttpServletResponse response) {
        final String userId = extracted(request);
        return ResponseEntity.ok().body(delegate.fetchMovieFromWatchlist(id, userId));
    }

    private String extracted(HttpServletRequest request) {
        final String authHeader = request.getHeader("authorization");
        final String token = authHeader.substring(7);
        String userId = Jwts.parser().setSigningKey("movie").parseClaimsJws(token).getBody().getSubject();
        return userId;
    }

    @Getter
    @AllArgsConstructor
    @ApiIgnore
    private class ApiResponseMessage {
        private String message;
    }
}