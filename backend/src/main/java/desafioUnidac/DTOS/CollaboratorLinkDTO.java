package desafioUnidac.DTOS;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CollaboratorLinkDTO implements Serializable{
    
    private Long coffee_id;
    @NotBlank
	@Size(max = 60)
    private String coffee_descricao;
    private Long collaborador_id;

    public CollaboratorLinkDTO(){

    }

    public CollaboratorLinkDTO(Long coffee_id, Long collaborador_id, String coffee_descricao){
        this.coffee_id = coffee_id;
        this.collaborador_id = collaborador_id;
        this.coffee_descricao = coffee_descricao;
    }

    public Long getCoffee_id() {
        return coffee_id;
    }
    public void setCoffee_id(Long coffee_id) {
        this.coffee_id = coffee_id;
    }

     public String getCoffee_descricao() {
         return coffee_descricao;
     }
     public void setCoffee_descricao(String coffee_descricao) {
         this.coffee_descricao = coffee_descricao;
     }
     public Long getCollaborador_id() {
         return collaborador_id;
     }
     public void setCollaborador_id(Long collaborador_id) {
         this.collaborador_id = collaborador_id;
     }
}

