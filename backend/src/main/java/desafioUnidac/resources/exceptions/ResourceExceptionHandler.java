package desafioUnidac.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import desafioUnidac.services.exceptions.BadRequestException;
import desafioUnidac.services.exceptions.DatabaseException;
import desafioUnidac.services.exceptions.DuplicateCoffeeException;
import desafioUnidac.services.exceptions.DuplicateCpfException;
import desafioUnidac.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> resourceNotFound(DatabaseException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<StandardError> resourceBadRequest(BadRequestException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DuplicateCpfException.class)
	public ResponseEntity<StandardError> resourceDuplicateEmail(DuplicateCpfException e, HttpServletRequest request) {
		String error = "Duplicate cpf";
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DuplicateCoffeeException.class)
	public ResponseEntity<StandardError> resourceDuplicateEmail(DuplicateCoffeeException e,
			HttpServletRequest request) {
		String error = "Duplicate coffee";
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> resourceValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
		String error = "Erro de validação";
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError(Instant.now(), status.value(), error, "", request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.setMessage(err.getMessage() + x.getField() + " " + x.getDefaultMessage() + "\n");
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(err);
	}

}
