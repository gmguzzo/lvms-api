/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.person;

import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.roleperson.RolePerson;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@SequenceGenerator(sequenceName = "SEQ_PERSON", name = "SEQ_PERSON", allocationSize = 1, initialValue = 1)
public class Person extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON")
    private Long id;

    @Column(name = "username", columnDefinition = "text", nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "text", nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.EAGER)
    private Set<RolePerson> rolePersons = new HashSet<>();

    public Person(Long id) {
        this.id = id;
    }

}
