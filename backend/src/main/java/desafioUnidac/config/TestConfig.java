package desafioUnidac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import desafioUnidac.model.Collaborator;
import desafioUnidac.repositorys.CoffeeRepository;
import desafioUnidac.repositorys.CollaboratorRespository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private CollaboratorRespository collaboratorRepository;

	@Autowired
	private CoffeeRepository coffeeRepository;

	@Override
	public void run(String... args) throws Exception {

				// Coffee pao = new Coffee("Pao");
		// Coffee bolo = new Coffee("Bolo");
		// Coffee queijo = new Coffee("queijo");

		// // Collaborator paulo = new Collaborator("Paulo", "12345678978",Arrays.asList(pao,bolo));
		// Collaborator duda = new Collaborator("Duda", "12345678978",Arrays.asList(queijo));

		collaboratorRepository.inserir("Paulo", "12345678978");
		collaboratorRepository.inserir("Duda", "12345678972");

		Collaborator paulo = collaboratorRepository.consultaCpf("12345678978").get();
		Collaborator duda = collaboratorRepository.consultaCpf("12345678972").get();

		
		coffeeRepository.inserirLink("pao", paulo.getId());
		coffeeRepository.inserirLink("bolo", paulo.getId());
		coffeeRepository.inserirLink("queijo", duda.getId());

		// pao = coffeeRepository.consultaDescricao("pao").get();
		// bolo = coffeeRepository.consultaDescricao("bolo").get();

		// collaboratorRepository.inserirLink(paulo.getId(), pao.getId());
		// collaboratorRepository.inserirLink(paulo.getId(), bolo.getId());


	}

}
