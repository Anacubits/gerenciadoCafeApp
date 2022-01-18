package desafioUnidac.services.exceptions;

import org.hibernate.service.spi.ServiceException;

public class BadRequestException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);

	}

	public BadRequestException() {
		super("BadRequest");

	}

}
