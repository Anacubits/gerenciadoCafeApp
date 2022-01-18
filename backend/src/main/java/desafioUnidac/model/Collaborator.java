package desafioUnidac.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Collaborator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 60)
	private String nome;
	@CPF
	private String cpf;

	@Transient
	@ManyToOne
	private List<Coffee> listCoffees = new ArrayList<>();

	public Collaborator() {

	}

	public Collaborator(String nome, String cpf) {
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

	@Override
	public String toString() {
		return "Collaborator [id=" + id + ", nome=" + nome + ", cpf=" + cpf + "]";
	}

}
