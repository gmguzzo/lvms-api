package br.com.louvemos.api.personshare;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.stereotype.Component;

@Component
public class PersonShareConverter {

    public PersonShare toModel(Long id, PersonShareDTO pd) {
        if (pd == null) {
            if (id == null) {
                return null;
            }
            return new PersonShare(id);
        }

        PersonShare p = new PersonShare();

        p.setId(id);
        p.setSubjectType(PersonShareSubjectTypeEnum.valueOf(pd.getSubjectType()));
        p.setSubjectId(pd.getSubjectId());

        return p;
    }

}