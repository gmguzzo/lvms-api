/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.personshare;

import br.com.louvemos.api.person.PersonDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author gmguzzo
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonShareDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String subjectType;
    private Long subjectId;
    private PersonDTO ownerPerson;
    private PersonDTO targetPerson;

}
