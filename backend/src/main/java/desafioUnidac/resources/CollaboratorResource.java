package desafioUnidac.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafioUnidac.DTOS.CollaboratorDTO;
import desafioUnidac.model.Collaborator;
import desafioUnidac.services.CollaboratorService;

//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "https://cafeunidac.netlify.app")
@RestController
@RequestMapping(value = "/collaborator")
public class CollaboratorResource {

	@Autowired
	private CollaboratorService service;

	@GetMapping
	public ResponseEntity<List<Collaborator>> findAll() {
		List<Collaborator> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Collaborator> findById(@RequestBody @PathVariable Long id) {
		Collaborator collaborator = service.findById(id);
		return ResponseEntity.ok().body(collaborator);
	}

	@PostMapping()
	public ResponseEntity<Collaborator> insert(@Valid @RequestBody Collaborator obj) {
		Collaborator collaborator;
		collaborator = service.insert(obj);
		return ResponseEntity.status(HttpStatus.CREATED).body(collaborator);
	}

	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody CollaboratorDTO obj) {
		service.update(obj);
		return ResponseEntity.ok().body("Resgistro atualizado com sucesso!");
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
