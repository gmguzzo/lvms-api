/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.base;

import br.com.louvemos.api.chord.ChordDTO;
import br.com.louvemos.api.song.SongDTO;
import br.com.louvemos.api.album.AlbumDTO;
import br.com.louvemos.api.artist.ArtistDTO;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nextChord;
    private Long code;
    private Integer httpStatus;
    private String message;

    private List<ChordDTO> chords;
    private ChordDTO chord;

    private List<SongDTO> songs;
    private SongDTO song;

    private AlbumDTO album;

    private ArtistDTO artist;

}
