/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.chord;

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
public class ChordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String symbol;

    private Long startFret;

    private Long bar;

    private Long bassString;

    private List<Long> soundedStrings;

    private List<Long> mutedStrings;

    private List<List<Long>> diagram;

    public ChordDTO(Long id) {
        this.id = id;
    }

}
