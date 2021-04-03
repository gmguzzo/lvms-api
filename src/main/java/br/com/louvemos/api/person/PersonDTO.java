/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.person;

import br.com.louvemos.api.personshare.PersonShareDTO;
import br.com.louvemos.api.role.RoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author gmguzzo
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private RoleDTO role;
    private PersonShareDTO personShare;
    private List<PersonShareDTO> sharedResources;
    private List<RoleDTO> roles;

    public PersonDTO(Long id) {
        this.id = id;
    }

}
