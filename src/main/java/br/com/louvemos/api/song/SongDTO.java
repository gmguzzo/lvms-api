/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.song;

import br.com.louvemos.api.person.PersonDTO;
import br.com.louvemos.api.album.*;
import br.com.louvemos.api.category.*;
import br.com.louvemos.api.externallink.*;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class SongDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long index;

    private String title;

    private String description;

    private String lyric;

    private String status;

    private String tone;

    private AlbumDTO album;

    private Boolean isPublic;

    private PersonDTO person;

    private List<CategoryDTO> categories;

    private List<ExternalLinkDTO> externalLinks;

    public SongDTO(Long id) {
        this.id = id;
    }

}
