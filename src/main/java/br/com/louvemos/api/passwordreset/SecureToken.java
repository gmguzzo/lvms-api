/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.passwordreset;

/**
 *
 * @author gmguzzo
 */
import br.com.louvemos.api.base.BaseEntity;
import br.com.louvemos.api.person.Person;

import javax.persistence.*;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SecureToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @Column(name = "expire_at")
    private ZonedDateTime expireAt;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_person_id"))
    private Person person;

    public boolean isExpired() {
        return getExpireAt().isBefore(ZonedDateTime.now()); // this is generic implementation, you can always make it timezone specific
    }

}
