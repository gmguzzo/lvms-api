/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.songcategory;

import br.com.louvemos.api.song.Song;
import br.com.louvemos.api.category.Category;
import br.com.louvemos.api.base.BaseEntity;
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
@SequenceGenerator(sequenceName = "SEQ_SONG_CATEGORY", name = "SEQ_SONG_CATEGORY", allocationSize = 1, initialValue = 1)
public class SongCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SONG_CATEGORY")
    private Long id;

    @Column(name = "main", columnDefinition = "boolean", nullable = false)
    private boolean main = false;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "song_id", nullable = false, foreignKey = @ForeignKey(name = "fk_song_id"))
    private Song song;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_category_id"))
    private Category category;

    public SongCategory(Long id) {
        this.id = id;
    }

}
