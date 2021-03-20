/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.song;

import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.album.Album;

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

import br.com.louvemos.api.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gmguzzo
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(sequenceName = "SEQ_SONG", name = "SEQ_SONG", allocationSize = 1, initialValue = 1)
public class Song extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SONG")
    private Long id;

    @Column(name = "title", columnDefinition = "text", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "text", nullable = false)
    private String description;

    @Column(name = "lyric", columnDefinition = "text", nullable = false)
    private String lyric;

    @Column(name = "tone", columnDefinition = "text", nullable = false)
    private String tone;

    @Column(name = "status", columnDefinition = "text", nullable = false)
    private String status;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id", nullable = false, foreignKey = @ForeignKey(name = "fk_album_id"))
    private Album album;

    @OneToMany(mappedBy = "song", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Category> categories;

    public Song(Long id) {
        this.id = id;
    }

}
