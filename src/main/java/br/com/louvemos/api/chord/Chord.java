/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.chord;

import br.com.louvemos.api.base.BaseEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 *
 * @author gmguzzo
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(sequenceName = "SEQ_CHORD", name = "SEQ_CHORD", allocationSize = 1, initialValue = 1)
public class Chord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHORD")
    private Long id;

    @Column(name = "symbol")
    private String symbol;
    
    @Column(name = "start_fret")
    private Long startFret;
    
    @Column(name = "bar")
    private Long bar;

    @Column(name = "bass_string")
    private Long bassString;

    @Column(name = "sounded_strings", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<Long> soundedStrings;

    @Column(name = "muted_strings", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<Long> mutedStrings;

    @Column(name = "diagram", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private List<List<Long>> diagram;

    public Chord(Long id) {
        this.id = id;
    }

}
