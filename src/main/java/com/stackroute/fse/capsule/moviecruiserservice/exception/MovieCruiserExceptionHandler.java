package com.stackroute.fse.capsule.moviecruiserservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class MovieCruiserExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Customize the response for MovieNotFoundException.
	 * 
	 * @param ex the exception
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@ExceptionHandler({ MovieNotFoundException.class })
	public ResponseEntity<Object> handleMovieNotFound(MovieNotFoundException ex, WebRequest request) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ApiError("Movie not found"));
	}
	
	/**
	 * Customize the response for MovieAlreadyExistException.
	 * 
	 * @param ex the exception
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@ExceptionHandler({ MovieAlreadyExistException.class })
	public ResponseEntity<Object> handleMovieNotFound(MovieAlreadyExistException ex, WebRequest request) {
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(new ApiError("Movie already exists"));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ex.getBindingResult());
	}
	
	@Getter
	@AllArgsConstructor
	private class ApiError {
		
		private String message;
	}
}