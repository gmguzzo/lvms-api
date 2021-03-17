/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.setlist;

import br.com.louvemos.api.base.BaseEntity;
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
@SequenceGenerator(sequenceName = "SEQ_SETLIST", name = "SEQ_SETLIST", allocationSize = 1, initialValue = 1)
public class Setlist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SETLIST")
    private Long id;

    @Column(name = "name", columnDefinition = "text", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_person_id"))
    private Person person;

    public Setlist(Long id) {
        this.id = id;
    }

}
