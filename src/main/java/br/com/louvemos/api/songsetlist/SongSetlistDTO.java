/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.songsetlist;

import br.com.louvemos.api.song.*;
import br.com.louvemos.api.setlist.*;
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
public class SongSetlistDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private SongDTO song;
    private SetlistDTO setlist;

    public SongSetlistDTO(Long id) {
        this.id = id;
    }

}
