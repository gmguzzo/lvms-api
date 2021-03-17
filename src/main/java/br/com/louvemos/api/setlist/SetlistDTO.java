/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.setlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import br.com.louvemos.api.person.PersonDTO;
import br.com.louvemos.api.song.SongDTO;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gmguzzo
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetlistDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private Boolean isPublic;
    private List<SongDTO> songs;
    private PersonDTO person;

    public SetlistDTO(Long id) {
        this.id = id;
    }

}
