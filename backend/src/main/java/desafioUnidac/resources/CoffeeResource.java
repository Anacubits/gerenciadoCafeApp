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

import desafioUnidac.DTOS.CollaboratorLinkDTO;
import desafioUnidac.model.Coffee;
import desafioUnidac.services.CoffeeService;

//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "https://cafeunidac.netlify.app")
@RestController
@RequestMapping(value= "/coffee")
public class CoffeeResource {
	
	@Autowired
	private CoffeeService service;
	
	@GetMapping
	public ResponseEntity<List<Coffee>> findAll(){
		List<Coffee> listCoffees = service.findAll();
		return ResponseEntity.ok().body(listCoffees);
	}
	
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Coffee> findById(@RequestBody @PathVariable Long id){
		Coffee coffee = service.consultaId(id);
		return ResponseEntity.ok().body(coffee);
	}
	
	@GetMapping(value = "/description/{descricao}")
	public ResponseEntity<Boolean> findByDescription(@RequestBody @PathVariable String descricao){
		Boolean isCoffee = (service.consultaDescricao(descricao)).isPresent() ;
		return ResponseEntity.ok().body(isCoffee);
	}

	@PostMapping()
	public ResponseEntity<Coffee> insert(@Valid @RequestBody CollaboratorLinkDTO obj){
		Coffee coffee;
			coffee = service.insertLink(obj.getCoffee_descricao(), obj.getCollaborador_id());
		return ResponseEntity.status(HttpStatus.CREATED).body(coffee);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody CollaboratorLinkDTO obj){
		service.update(obj);
		return ResponseEntity.ok().body("Resgistro atualizado com sucesso!");
	}
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok().body("Deletado com sucesso"); 
	}

}