/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.album;

import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.artist.Artist;
import br.com.louvemos.api.person.Person;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@SequenceGenerator(sequenceName = "SEQ_ALBUM", name = "SEQ_ALBUM", allocationSize = 1, initialValue = 1)
public class Album extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ALBUM")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", nullable = false, foreignKey = @ForeignKey(name = "fk_artist_id"))
    private Artist artist;

    @Column(name = "album_name", nullable = false)
    private String albumName;

    @Column(name = "genre")
    private String genre;

    @Column(name = "debut")
    private String debut;
    
    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;
    
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_person_id"))
    private Person person;

    public Album(Long id) {
        this.id = id;
    }

}
