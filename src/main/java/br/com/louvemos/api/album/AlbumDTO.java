/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.album;

import br.com.louvemos.api.artist.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
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
public class AlbumDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String albumName;

    private String genre;

    private String debut;

    private ArtistDTO artist;

    public AlbumDTO(Long id) {
        this.id = id;
    }

}
