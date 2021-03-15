/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.person;

import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class PersonServiceValidator {

    public void validatePersonFound(Person p) throws LvmsException {
        validateNull(p);
    }

    public void validatePersist(Person p) throws LvmsException {
        validateNull(p);

        validateUsername(p);
        validatePassword(p);
    }

    private void validateNull(Person pPersist) throws LvmsException {
        if (pPersist == null) {
            throw new LvmsException(LvmsCodesEnum.PERSON_NULL);
        }
    }

    private void validateUsername(Person p) throws LvmsException {
        if (StringUtils.isBlank(p.getUsername())) {
            throw new LvmsException(LvmsCodesEnum.PERSON_USERNAME_INVALID);
        }
    }

    private void validatePassword(Person p) throws LvmsException {
        if (StringUtils.isBlank(p.getPassword())) {
            throw new LvmsException(LvmsCodesEnum.PERSON_PASSWORD_INVALID);
        }
    }

}
