/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.artist;

import br.com.louvemos.api.album.AlbumDTO;
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
public class ArtistDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String artistName;

    private String genre;

    private String since;

    private List<AlbumDTO> albums;

    public ArtistDTO(Long id) {
        this.id = id;
    }

}
