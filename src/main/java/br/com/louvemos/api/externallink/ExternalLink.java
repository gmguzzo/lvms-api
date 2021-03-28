/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.song.Song;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @author heits
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(sequenceName = "SEQ_EXTERNAL_LINK", name = "SEQ_EXTERNAL_LINK", allocationSize = 1, initialValue = 1)
public class ExternalLink extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXTERNAL_LINK")
    private Long id;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "song_id", nullable = false, foreignKey = @ForeignKey(name = "fk_song_id"))
    private Song song;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ExternalLinkTypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(name = "media", nullable = false)
    private ExternalLinkMediaEnum media;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    private String url;

    public ExternalLink(Long id) {
        this.id = id;
    }

}
