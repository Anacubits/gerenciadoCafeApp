package desafioUnidac.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import desafioUnidac.DTOS.CollaboratorDTO;
import desafioUnidac.model.Coffee;
import desafioUnidac.model.Collaborator;
import desafioUnidac.repositorys.CollaboratorRespository;
import desafioUnidac.services.exceptions.BadRequestException;
import desafioUnidac.services.exceptions.DuplicateCpfException;
import desafioUnidac.services.exceptions.ResourceNotFoundException;


@Service
public class CollaboratorService {

	@Autowired
	private CollaboratorRespository collaboratorRespository;

	@Autowired
	private CoffeeService coffeeService;

	public List<Collaborator> findAll() {
		List<Collaborator> listCollaborator = collaboratorRespository.buscarTodos();

		for (Collaborator element : listCollaborator) {
			element.setListCoffees(coffeeService.buscarTodosCollaboratorId(element.getId()));
		}

		return listCollaborator;
	}

	public Collaborator findById(Long id) {
		Collaborator result = collaboratorRespository.consultaId(id).orElseThrow(() -> new ResourceNotFoundException(id));
		result.setListCoffees(coffeeService.buscarTodosCollaboratorId(result.getId()));
		return result;
	}

	public Collaborator insert(Collaborator obj) {
		Collaborator newCollaborator = null;
		if (validationInsert(obj)) {
			collaboratorRespository.inserir(obj.getNome(), obj.getCpf());
			newCollaborator = collaboratorRespository.consultaCpf(obj.getCpf()).get();
			for (Coffee element : obj.getListCoffees()) {
				coffeeService.insertLink(element.getDescricao(), newCollaborator.getId());
			}
		}

		return findById(newCollaborator.getId());
	}

	public Collaborator update(CollaboratorDTO c) {
		if (c.getId() == null){
			throw new BadRequestException("Id invalido!");
		}
		findById(c.getId());
		if (collaboratorRespository.consultaCpf(c.getCpf()).get().getId() != c.getId()){
			throw new DuplicateCpfException();
		}

		collaboratorRespository.atualiza(c.getId(), c.getNome(), c.getCpf());
		Collaborator collaborator = findById(c.getId());
		return collaborator;
	}

	public void delete(Long id) {
		if (findById(id) != null) {
			coffeeService.buscarTodosCollaboratorId(id).forEach(item -> {
				coffeeService.delete(item.getId());
			});
			collaboratorRespository.delete(id);
		}
	}

	public boolean validationInsert(Collaborator obj) {
		boolean result = true;
		if (collaboratorRespository.consultaCpf(obj.getCpf()).isPresent()) {
			throw new DuplicateCpfException(obj.getCpf());
		}
		return result;
	}
}
