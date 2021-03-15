/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.roleperson;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class RolePersonServiceValidator {

    public void validatePersist(RolePerson rp) throws LvmsException {
        validateNull(rp);
        validateRole(rp);
        validatePerson(rp);
    }

    public void validateNull(RolePerson rpPersist) throws LvmsException {
        if (rpPersist == null) {
            throw new LvmsException(LvmsCodesEnum.ROLE_NULL);
        }
    }

    private void validatePerson(RolePerson rp) throws LvmsException {
        if (rp.getPerson() == null) {
            throw new LvmsException(LvmsCodesEnum.PERSON_NULL);
        }
    }

    private void validateRole(RolePerson rp) throws LvmsException {
        if (rp.getRole() == null) {
            throw new LvmsException(LvmsCodesEnum.PERSON_NULL);
        }
    }

}
