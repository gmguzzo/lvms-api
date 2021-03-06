/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.album;

import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.artist.Artist;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
@Entity
@SequenceGenerator(sequenceName = "SEQ_ARTIST", name = "SEQ_ARTIST", allocationSize = 1, initialValue = 1)
public class Album extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARTIST")
    private Long id;

    @OneToOne(mappedBy = "album", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Artist artist;

    @Column(name = "album_name", nullable = false)
    private String albumName;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "debut")
    private String debut;

    public Album(Long id) {
        this.id = id;
    }

}