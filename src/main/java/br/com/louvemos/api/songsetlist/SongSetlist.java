/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.songsetlist;

import br.com.louvemos.api.setlist.*;
import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.song.Song;
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
@SequenceGenerator(sequenceName = "SEQ_SETLIST", name = "SEQ_SETLIST", allocationSize = 1, initialValue = 1)
public class SongSetlist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SETLIST")
    private Long id;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "song_id", nullable = false, foreignKey = @ForeignKey(name = "fk_song_id"))
    private Song song;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "setlist_id", nullable = false, foreignKey = @ForeignKey(name = "fk_setlist_id"))
    private Setlist setlist;

    public SongSetlist(Long id) {
        this.id = id;
    }

}
