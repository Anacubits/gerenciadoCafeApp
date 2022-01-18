package desafioUnidac.services.exceptions;

public class DuplicateCpfException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DuplicateCpfException(String cpf) {
		super("CPF "+ cpf+ " já cadastrado!");
	}

	public DuplicateCpfException() {
		super(" CPF já cadastrado!");
	}
}
