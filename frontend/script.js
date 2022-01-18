const URL_API = "https://desafioapp.herokuapp.com";
//const URL_API = "http://localhost:8080";

class Cafe {
  constructor(id, descricao) {
    this.id = id;
    this.descricao = descricao;
  }
}
class Collaborator {
  constructor(id, nome, cpf, listCoffees) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.listCoffees = listCoffees;
  }
}

class CollaboratorDTO {
  constructor(coffee_id, coffee_descricao, collaborador_id) {
    this.coffee_id = coffee_id;
    this.coffee_descricao = coffee_descricao;
    this.collaborador_id = collaborador_id;
  }
}

var itemCafe = [];
let contCafe = itemCafe.length;
let indiceEdite = 0;

var listaColaborador = [];
let contColaborador = listaColaborador.length;
let indiceEditeColaborador = 0;
let isFormularioEdicao = false;

const descricao = document.querySelector("#FormControlDescricao");
const nome = document.querySelector("#FormControlName");
const cpf = document.querySelector("#FormControlCpf");

cpf.value = "";
nome.value = "";

async function buscarTodosColaborador() {
  const colaboradores = fetch(`${URL_API}/collaborator`).then((resp) =>
    resp.json()
  );

  const result = await colaboradores;
  return result;
}

async function buscarCpf(cpf) {
  const consultarCpf = fetch(`${URL_API}/collaborator/cpf/${cpf}`)
    .then((resp) => resp.json())
    .then((data) => {
      const isCoffee = data == true ? true : false;
      return isCoffee;
    });
  return await consultarCpf;
}

async function buscarCoffeeDes(descricao) {
  const coffeeEdit = fetch(`${URL_API}/coffee/description/${descricao}`)
    .then((resp) => resp.json())
    .then(function (data) {
      //console.log(data);
      const isCoffee = data == true ? true : false;
      return isCoffee;
    });
  const result = await coffeeEdit;
  return result;
}

function editarColaborador(id) {
  const h1Cadastro = document.querySelector("#h1Cadastro");
  const h1Editar = document.querySelector("#h1Editar");
  const btnFormCadastrar = document.querySelector("#btnFormCadastrar");

  h1Cadastro.style.display = "none";
  h1Editar.style.display = "block";
  cpf.setAttribute("readOnly", "false");
  isFormularioEdicao = true;
  nome.value = listaColaborador[id - 1].nome;
  cpf.value = listaColaborador[id - 1].cpf;
  itemCafe = listaColaborador[id - 1].listCoffees;
  ListarCafes();
  btnFormCadastrar.innerHTML = "Alterar";
  indiceEditeColaborador = id;
}

async function cadastrarColabordor() {
  let collaborador;
  if (isFormularioEdicao) {
    collaborador = new Collaborator(
      listaColaborador[indiceEditeColaborador - 1].id,
      nome.value,
      cpf.value,
      itemCafe
    );
  } else {
    collaborador = new Collaborator(null, nome.value, cpf.value, itemCafe);
  }

  let fetchData = {
    method: isFormularioEdicao ? "PUT" : "POST",
    body: JSON.stringify(collaborador),
    headers: {
      "Content-Type": "application/json",
    },
  };

  const response = await fetch(URL_API + "/collaborator", fetchData).then(
    async (response) => {
      if (response.status == 201) {
        alert("Colaborador cadastrado com sucesso!");
      } else if (response.status == 200) {
        alert("Colaborador atualizado com sucesso!");
      } else {
        const res = await response.json();
        alert(`${res.error}\nMenssagem: ${res.message}`);
      }
      return await response;
    }
  );

  if (response.status == 201 || response.status == 200) {
    itemCafe = [];
    contCafe = itemCafe.length;
    indiceEdite = 0;

    nome.value = "";
    cpf.value = "";

    if (isFormularioEdicao) voltarFormularioCadastro();
    isFormularioEdicao = false;

    ListarCafes();
    ListarColaborador();
  } else console.log("nao esperou a resposta");
}

function voltarFormularioCadastro() {
  const h1Cadastro = document.querySelector("#h1Cadastro");
  const h1Editar = document.querySelector("#h1Editar");
  const btnFormCadastrar = document.querySelector("#btnFormCadastrar");

  h1Cadastro.style.display = "block";
  h1Editar.style.display = "none";
  cpf.removeAttribute("readOnly");
  isFormularioEdicao = false;
  itemCafe = [];
  btnFormCadastrar.innerHTML = "Cadastrar";
  indiceEditeColaborador = 0;
}

async function ListarColaborador() {
  listaColaborador = [];
  let todosColaborador = await buscarTodosColaborador();
  todosColaborador.forEach((item) => {
    listaColaborador.push(item);
  });
  if (true) {
    const tbody = document.querySelector("#tbodyColaborador");
    tbody.innerHTML = "";
    for (let index = 0; index < listaColaborador.length; index++) {
      tbody.innerHTML += `
          <tr>
              <th scope="row">${index + 1}</th>
              <td colspan="2">${listaColaborador[index].nome}</td>
              <td colspan="2">${listaColaborador[index].cpf}</td>
              <td>
                <button type="button" class="btn btn-warning" onclick="editarColaborador(${
                  index + 1
                })" id="btnEdit">
                <img src="./assets/edit-alt.svg" id="svgEdit" alt="">
                </button>
                <button type="button" class="btn btn-danger" onclick="deletarColaborador(${
                  index + 1
                })" id="btnDelColaborador">
                <img src="./assets/ui-delete.svg" id="svgDelete" alt="">
                </button>
              </td>
          </tr>
  
              `;
    }
  }
}
ListarColaborador();

function ListarCafes() {
  const tbody = document.querySelector("#tbodyCafe");
  tbody.innerHTML = "";
  for (let index = 0; index < itemCafe.length; index++) {
    tbody.innerHTML += `
                <tr>
                    <th scope="row">${index + 1}</th>
                    <td colspan="2">${itemCafe[index].descricao}</td>
                    <td>
                        <button type="button" class="btn btn-warning" onclick="abrirmodalEditar(${
                          index + 1
                        })" id="btnEdit">
                        <img src="./assets/edit-alt.svg" id="svgEdit" alt="">
                        </button>
                        <button type="button" class="btn btn-danger" onclick="deletarCafe(${
                          index + 1
                        })" id="btnDel">
                        <img src="./assets/ui-delete.svg" id="svgDelete" alt="">
                        </button>
                    </td>
                    </tr>
            `;
  }
  descricao.value = "";
}
ListarCafes();

async function addCafeList() {
  const descricao = document.querySelector("#FormControlDescricao").value;
  let isCoffee = false;
  if (descricao) {
    isCoffee = await buscarCoffeeDes(descricao.toLowerCase());

    if (!isCoffee) {
      //console.log(indiceEditeColaborador);
      if (isFormularioEdicao) {
        const collaboratorDTO = new CollaboratorDTO(
          null,
          descricao,
          listaColaborador[indiceEditeColaborador - 1].id
        );
        adicionarCafeBd(collaboratorDTO);
      }
      contCafe = itemCafe.length + 1;
      const newCafe = new Cafe(contCafe, descricao);
      itemCafe.push(newCafe);
      ListarCafes();
    } else {
      alert(`Atenção!\n${descricao} já está cadastrado`);
    }
  } else {
    alert(`Atenção!\nCampo em branco\n!Descrição do café! `);
  }
}

async function deleteColaboradoBd(id) {
  const response = await fetch(`${URL_API}/collaborator/${id}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: null,
  }).then((response) => response);

  const data = await response;
  if (data != null) {
    ListarColaborador();
  }
}

async function adicionarCafeBd(collaboratorDTO) {
  let fetchData = {
    method: "POST",
    body: JSON.stringify(collaboratorDTO),
    headers: {
      "Content-Type": "application/json",
    },
  };

  const response = await fetch(`${URL_API}/coffee`, fetchData).then(
    (response) => {
      console.log(response);
      if (response.status == 201) {
        alert("Café adicionado com sucesso!");
      } else {
        //const res = await response.json();
        alert(`${res.error}\nMenssagem: ${res.message}`);
      }
    }
  );

  const data = await response;
  if (data != null) {
    ListarCafes();
  }
}

async function editarCafeBd(collaboratorDTO) {
  let fetchData = {
    method: "PUT",
    body: JSON.stringify(collaboratorDTO),
    headers: {
      "Content-Type": "application/json",
    },
  };

  const response = await fetch(`${URL_API}/coffee`, fetchData).then(
    (response) => {
      if (response == 200) alert("Item editado com sucesso");
    }
  );

  const data = await response;
  if (data != null) {
    ListarCafes();
  }
}

async function deleteCafeBd(id) {
  const response = await fetch(`${URL_API}/coffee/${id}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: null,
  }).then((response) => {
    if (response == 200) alert("Item removido com sucesso");
  });

  const data = await response;
  if (data != null) {
    ListarCafes();
  }
}

function closeModalEdit() {
  const modalEditar = document.querySelector("#modalEditar");
  modalEditar.style.visibility = "collapse";
}
function abrirmodalEditar(id) {
  const modalEditar = document.querySelector("#modalEditar");
  modalEditar.style.visibility = "visible";
  const inputEditar = document.querySelector("#FormControlDescricaoEdit");
  itemCafe.forEach((item) => {
    if (item.id == id) {
      inputEditar.value = item.descricao;
    }
  });
  indiceEdite = id;
}

function closeModalConsultar() {
  const modalConsultar = document.querySelector("#modalConsulatr");
  modalConsultar.style.visibility = "collapse";
}
function abrirmodalConsulta() {
  const modalConsultar = document.querySelector("#modalConsulatr");
  modalConsultar.style.visibility = "visible";
  const inputEditar = document.querySelector("#FormControlDescricaoEdit");
  itemCafe.forEach((item) => {
    if (item.id == id) {
      inputEditar.value = item.descricao;
      indiceEdite = id;
    }
  });
}

async function consultarCPF() {
  const inputCpf = document.querySelector("#FormControlDescricaoEdit").value;
  const isCpf = await buscarCpf(inputCpf.toLowerCase());
}

async function editarCafe() {
  const inputEditar = document.querySelector("#FormControlDescricaoEdit").value;
  if (inputEditar) {
    const isCoffee = await buscarCoffeeDes(inputEditar.toLowerCase());
    if (!isCoffee) {
      if (isFormularioEdicao) {
        const collaboratorDTO = new CollaboratorDTO(
          itemCafe[indiceEdite - 1].id,
          inputEditar,
          listaColaborador[indiceEditeColaborador - 1].id
        );
        editarCafeBd(collaboratorDTO);
      }
      for (let index = 0; index < itemCafe.length; index++) {
        if (index == indiceEdite - 1) itemCafe[index].descricao = inputEditar;
      }
      closeModalEdit();
      ListarCafes();
    } else {
      alert(`Atenção!\n${inputEditar} já está cadastrado`);
    }
  } else {
    alert(`Atenção!\nCampo em branco\n!Descrição do café! `);
  }
}

function deletarCafe(id) {
  alert("deletar ID" + id);

  //console.log(itemCafe[id-1].id);
  if (isFormularioEdicao) deleteCafeBd(itemCafe[id - 1].id);
  for (let index = 0; index < itemCafe.length; index++) {
    if (index == id - 1) itemCafe.splice(id - 1, 1);
    //console.log(itemCafe[index]);
  }
  ListarCafes();
}

function deletarColaborador(id) {
  alert("deletar ID" + id);
  deleteColaboradoBd(listaColaborador[id - 1].id);
}
