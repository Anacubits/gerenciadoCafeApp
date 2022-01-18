package desafioUnidac.repositorys;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import desafioUnidac.model.Collaborator;

@Repository
public interface CollaboratorRespository extends JpaRepository<Collaborator, Long> {

	@Query(value = "select * FROM collaborator ORDER BY id", nativeQuery = true)
	List<Collaborator> buscarTodos();

	@Query(value = "SELECT * FROM collaborator as c WHERE c.id = :id", nativeQuery = true)
	Optional<Collaborator> consultaId(@Param("id") Long id);

	@Query(value = "SELECT * FROM collaborator as c WHERE c.cpf = :cpf", nativeQuery = true)
	Optional<Collaborator> consultaCpf(@Param("cpf") String cpf);

	@Transactional
	@Modifying
	@Query(value = "insert into collaborator (nome, cpf) values (:nome, :cpf)", nativeQuery = true)
	void inserir(@Param("nome") String name, @Param("cpf") String cpf);

	@Transactional
	@Modifying
	@Query(value = "update Collaborator set nome =:nome, cpf =:cpf WHERE id =:id", nativeQuery = true)
	void atualiza(@Param("id") Long id, @Param("nome") String name, @Param("cpf") String cpf);

	@Transactional
	@Modifying
	@Query(value = "delete FROM collaborator as c WHERE c.id = :id", nativeQuery = true)
	void delete(@Param("id") Long id);

	// @Transactional
	// @Modifying
	// @Query(value = "insert into collaborator_coffee (collaborator_id, coffee_id)
	// values (:collaborator_id, :coffee_id)", nativeQuery = true)
	// void inserirLink55(@Param("collaborator_id") Long collaborator_id,
	// @Param("coffee_id") Long coffe_id);

}
