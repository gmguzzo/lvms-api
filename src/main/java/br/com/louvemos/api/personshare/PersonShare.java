/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.personshare;

import br.com.louvemos.api.artist.Artist;
import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.roleperson.RolePerson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gmguzzo
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(sequenceName = "SEQ_PERSON_SHARE", name = "SEQ_PERSON_SHARE", allocationSize = 1, initialValue = 1)
public class PersonShare extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON_SHARE")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", columnDefinition = "text", nullable = false)
    private PersonShareSubjectTypeEnum subjectType;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_person_id", nullable = false, foreignKey = @ForeignKey(name = "fk_owner_person_id"))
    private Person ownerPerson;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_person_id", nullable = false, foreignKey = @ForeignKey(name = "fk_target_person_id"))
    private Person targetPerson;

    public PersonShare(Long id) {
        this.id = id;
    }

}
