package desafioUnidac.repositorys;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import desafioUnidac.model.Coffee;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

	@Query(value = "select * FROM coffee ORDER BY id", nativeQuery = true)
	List<Coffee> buscarTodos();

	@Query(value = "select * FROM coffee WHERE collaborator_id = :id  ORDER BY id", nativeQuery = true)
	List<Coffee> buscarTodosCollaboratorId(@Param("id") Long collcaborator_id);

	@Transactional
	@Modifying
	@Query(value = "insert into coffee (descricao) values (:descricao)", nativeQuery = true)
	void inserir(@Param("descricao") String descricao);

	@Transactional
	@Modifying
	@Query(value = "insert into coffee (descricao, collaborator_id) values (:descricao, :collaborator_id)", nativeQuery = true)
	void inserirLink(@Param("descricao") String descricao, @Param("collaborator_id") Long collaborator_id);

	@Query(value = "SELECT * FROM coffee as c WHERE c.id = :id", nativeQuery = true)
	Optional<Coffee> consultaId(@Param("id") Long id);

	@Query(value = "SELECT * FROM coffee as c WHERE c.descricao = :descricao", nativeQuery = true)
	Optional<Coffee> consultaDescricao(@Param("descricao") String descricao);

	@Transactional
	@Modifying
	@Query(value = "update coffee set descricao =:descricao WHERE id =:id", nativeQuery = true)
	void atualiza(@Param("id") Long coffe_id, @Param("descricao") String descricao);

	@Transactional
	@Modifying
	@Query(value = "delete FROM coffee as c WHERE c.id = :id", nativeQuery = true)
	void delete(@Param("id") Long id);

}
