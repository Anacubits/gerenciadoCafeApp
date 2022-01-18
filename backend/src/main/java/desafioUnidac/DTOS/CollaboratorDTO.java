package desafioUnidac.DTOS;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import desafioUnidac.model.Coffee;

public class CollaboratorDTO {

	private Long id;
	@NotBlank
	@Size(max = 60)
	private String nome;
	private String cpf;

	private List<Coffee> listCoffees = new ArrayList<>();

	public CollaboratorDTO() {

	}

	public CollaboratorDTO(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Coffee> getListCoffees() {
		return listCoffees;
	}

	public void setListCoffees(List<Coffee> listCoffees) {
		this.listCoffees = listCoffees;
	}

}
