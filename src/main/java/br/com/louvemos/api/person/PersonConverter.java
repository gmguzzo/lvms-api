package br.com.louvemos.api.person;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {

    public Person toModel(Long id, PersonDTO pd) {
        if (pd == null) {
            if (id == null) {
                return null;
            }
            return new Person(id);
        }

        Person p = new Person();

        p.setId(id);
        p.setUsername(pd.getUsername());
        p.setPassword(pd.getPassword());

        return p;
    }

    public PersonDTO toDTO(Person u) {
        if (u == null) {
            return null;
        }

        PersonDTO pd = new PersonDTO();
        pd.setId(u.getId());
        pd.setUsername(u.getUsername());
        pd.setPassword(u.getPassword());

        return pd;
    }

}
