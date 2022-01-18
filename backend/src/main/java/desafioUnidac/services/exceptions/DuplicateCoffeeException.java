package desafioUnidac.services.exceptions;

public class DuplicateCoffeeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DuplicateCoffeeException(String coffeDescricao) {
		super(coffeDescricao + " já cadastrado!");
	}

	public DuplicateCoffeeException() {
		super(" Coffee já cadastrado!");
	}
}
