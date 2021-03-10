/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.category;

import br.com.louvemos.api.song.*;
import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.album.Album;
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
@SequenceGenerator(sequenceName = "SEQ_CATEGORY", name = "SEQ_CATEGORY", allocationSize = 1, initialValue = 1)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORY")
    private Long id;

    @Column(name = "category_name", columnDefinition = "text", nullable = false)
    private String categoryName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    public Category(Long id) {
        this.id = id;
    }

}
