/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.artist;

import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.album.Album;
import br.com.louvemos.api.person.Person;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Artist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARTIST")
    private Long id;

    @OneToMany(mappedBy = "artist", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Album> albums;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "genre")
    private String genre;

    @Column(name = "since")
    private String since;
    
    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;
    
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_person_id"))
    private Person person; 

    public Artist(Long id) {
        this.id = id;
    }

}
