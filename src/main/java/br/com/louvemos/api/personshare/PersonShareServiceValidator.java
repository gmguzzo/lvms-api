/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.personshare;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class PersonShareServiceValidator {
    
    @Autowired
    private PersonShareService personShareService;
    
    public void validatePersonShareFound(PersonShare p) throws LvmsException {
        validateNull(p);
    }
    
    public void validatePersist(PersonShare p) throws LvmsException {
        validateNull(p);
        validateAlreadySharedResource(p);
        
    }
    
    private void validateNull(PersonShare pPersist) throws LvmsException {
        if (pPersist == null) {
            throw new LvmsException(LvmsCodesEnum.PERSON_NULL);
        }
    }
    
    private void validateAlreadySharedResource(PersonShare ps) throws LvmsException {
        PersonShare psPersist = personShareService.load(null, ps.getSubjectType(), ps.getSubjectId(), ps.getTargetPerson().getId());
        
        if (psPersist != null) {
            throw new LvmsException(LvmsCodesEnum.PERSON_SHARE_ALREADY_EXISTS);
        }
    }
    
}
