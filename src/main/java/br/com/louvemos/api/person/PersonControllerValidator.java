package br.com.louvemos.api.person;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PersonControllerValidator {

    public void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        PersonDTO pd = bdIn.getPerson();

        validatePerson(pd);
        validateUsername(pd);
        validatePassword(pd);

    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        PersonDTO pd = bdIn.getPerson();

        validatePerson(pd);
        validateUsername(pd);
        validatePassword(pd);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.PERSON_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.JSON_INVALID_FORMAT);
        }
    }

    private void validatePerson(PersonDTO pd) throws LvmsException {
        if (pd == null) {
            throw new LvmsException(LvmsCodesEnum.PERSON_NULL);
        }
    }

    private void validateUsername(PersonDTO pd) throws LvmsException {
        if (StringUtils.isBlank(pd.getUsername())) {
            throw new LvmsException(LvmsCodesEnum.PERSON_USERNAME_INVALID);
        }
    }

    private void validatePassword(PersonDTO pd) throws LvmsException {
        if (StringUtils.isBlank(pd.getPassword())) {
            throw new LvmsException(LvmsCodesEnum.PERSON_PASSWORD_INVALID);
        }
    }

}
