const URL_API = "https://desafioapp.herokuapp.com";
//const URL_API = "http://localhost:8080";

class Collaborator {
  constructor(id, nome, cpf, listCoffees) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.listCoffees = listCoffees;
  }
}

var listaColaborador = [];

async function buscarTodosColaborador() {
  const colaboradores = fetch(`${URL_API}/collaborator`).then((resp) =>
    resp.json()
  );

  const result = await colaboradores;
  return result;
}


async function ListarColaborador() {
  listaColaborador = [];
  let todosColaborador = await buscarTodosColaborador();
  todosColaborador.forEach((item) => {
    const colaborador = new Collaborator(
      item.id,
      item.nome,
      item.cpf,
      item.listCoffees
    );
    listaColaborador.push(colaborador);
  });
    const listCard = document.querySelector("#listCard");
    listCard.innerHTML = "";
    for (let index = 0; index < listaColaborador.length; index++) {
      listCard.innerHTML += `
    
        <div class="card mb-3" style="width: 250px;">
        <div class="header">
            ${listaColaborador[index].nome} <br> ${
        listaColaborador[index].cpf
      }</div>
        <div class="cardBody">
            <div class="body-container">
                <div class="scroll-body">
                    <ul id="listaCafe-${index + 1}">
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer">
            Cafe & Cia
        </div>
    </div>
    
        `;
      const listaCafe = document.querySelector(`#listaCafe-${index + 1}`);
      listaColaborador[index].listCoffees.forEach((item) => {
        listaCafe.innerHTML += `
            <li>${item.descricao}</li>
            `;
      });
  }
}
ListarColaborador();
