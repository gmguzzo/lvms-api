/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import br.com.louvemos.api.song.SongDTO;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author heits
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalLinkDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String type;
    private String media;
    private String url;
    private SongDTO song;

}
