package ca.mcgill.ecse321.onlinegallery.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when comment services fail.
 *
 */

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CreditCardException extends Exception{
	public CreditCardException(String msg) {
		super(msg);
	}
}
 