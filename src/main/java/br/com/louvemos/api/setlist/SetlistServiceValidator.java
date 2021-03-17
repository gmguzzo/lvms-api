/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.setlist;

import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class SetlistServiceValidator {

    public void validateSetlistFound(Setlist sPersist) throws LvmsException {
        validateNull(sPersist);
    }

    public void validatePersist(Setlist sPersist) throws LvmsException {
        validateName(sPersist);
        validateDescription(sPersist);
        validatePrivateSetlistHasPerson(sPersist);
    }

    private void validateNull(Setlist s) throws LvmsException {
        if (s == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NULL);
        }
    }

    private void validateDescription(Setlist s) throws LvmsException {
        if (s.getDescription() != null && StringUtils.isBlank(s.getDescription())) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_DESCRIPTION_INVALID);
        }
    }

    private void validateName(Setlist s) throws LvmsException {
        if (StringUtils.isBlank(s.getName())) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NAME_INVALID);
        }
    }

    private void validatePrivateSetlistHasPerson(Setlist sPersist) throws LvmsException {
        if (sPersist.isPublic()) {
            return;
        }

        if (sPersist.getPerson() == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_PRIVATE_LIST_MUST_HAVE_PERSON);
        }
    }

}
