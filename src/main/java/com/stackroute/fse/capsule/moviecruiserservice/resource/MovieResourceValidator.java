package com.stackroute.fse.capsule.moviecruiserservice.resource;

import java.math.BigInteger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateMovieResourceValidator")
public class MovieResourceValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Movie requestObject = (Movie) target;
		if(requestObject.getId() <= BigInteger.ZERO.intValue()) {
			errors.reject("id", "negative id");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Movie.class.isAssignableFrom(clazz);
	}

}
