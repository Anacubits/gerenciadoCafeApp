package desafioUnidac.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafioUnidac.DTOS.CollaboratorLinkDTO;
import desafioUnidac.model.Coffee;
import desafioUnidac.repositorys.CoffeeRepository;
import desafioUnidac.services.exceptions.BadRequestException;
import desafioUnidac.services.exceptions.DuplicateCoffeeException;
import desafioUnidac.services.exceptions.ResourceNotFoundException;

@Service
public class CoffeeService {

	@Autowired
	private CoffeeRepository coffeeRepository;

	public List<Coffee> findAll() {
		List<Coffee> listCoffees = coffeeRepository.buscarTodos();
		return listCoffees;
	}

	public List<Coffee> buscarTodosCollaboratorId(Long collcaborator_id) {
		return coffeeRepository.buscarTodosCollaboratorId(collcaborator_id);
	}


	public Coffee consultaId(Long coffee_id) {
		return coffeeRepository.consultaId(coffee_id).orElseThrow(() -> new ResourceNotFoundException(coffee_id));
	}

	public Optional<Coffee> consultaDescricao(String coffee_descricao) {
		return coffeeRepository.consultaDescricao(coffee_descricao);
		
	}

	public Coffee insert(String descricao) {
		if (validationInsert(descricao)) {
			coffeeRepository.inserir(descricao);
		}
		return consultaDescricao(descricao).get();
	}

	public Coffee insertLink(String descricao, Long collaborator_id) {
		if (validationInsert(descricao)) {
			coffeeRepository.inserirLink(descricao, collaborator_id);
		}
		return consultaDescricao(descricao).get();
	}

	public Coffee update(CollaboratorLinkDTO obj) {
		if (obj.getCoffee_id() == null)
			throw new BadRequestException("Id invalido!");
			consultaId(obj.getCoffee_id());
		coffeeRepository.atualiza(obj.getCoffee_id(), obj.getCoffee_descricao());
		Coffee newCoffee = consultaId(obj.getCoffee_id());
		return newCoffee;
	}

	public void delete(Long id) {
		if (consultaId(id) != null) {
			coffeeRepository.delete(id);
		}
	}

	public boolean validationInsert(String descricao) {
		boolean isValidated = true;
		if (coffeeRepository.consultaDescricao(descricao).isPresent()) {
			throw new DuplicateCoffeeException(descricao);
		}
		return isValidated;
	}
}
