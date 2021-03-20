package br.com.louvemos.api.person;

import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonControllerValidator {

    @Autowired
    private RoleService roleService;

    public void validateAssignRole(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        PersonDTO pd = bdIn.getPerson();

        validateRole(pd);

    }

    public void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        PersonDTO pd = bdIn.getPerson();

        validatePerson(pd);
        validateUsername(pd);
        validatePassword(pd);
        validateFirstName(pd);
        validateLastName(pd);
        validateEmail(pd);

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

    private void validateFirstName(PersonDTO pd) throws LvmsException {
        if (StringUtils.isBlank(pd.getFirstName())) {
            throw new LvmsException(LvmsCodesEnum.PERSON_FIRSTNAME_INVALID);
        }
    }

    private void validateLastName(PersonDTO pd) throws LvmsException {
        if (StringUtils.isBlank(pd.getLastName())) {
            throw new LvmsException(LvmsCodesEnum.PERSON_LASTNAME_INVALID);
        }
    }

    private void validateEmail(PersonDTO pd) throws LvmsException {
        if (StringUtils.isBlank(pd.getEmail())) {
            throw new LvmsException(LvmsCodesEnum.PERSON_EMAIL_INVALID);
        }
    }

    private void validateRole(PersonDTO pd) throws LvmsException {
        if (pd.getRole() == null) {
            throw new LvmsException(LvmsCodesEnum.ROLE_NULL);
        }

        if (roleService.load(null, pd.getRole().getName()) == null) {
            throw new LvmsException(LvmsCodesEnum.ROLE_NULL);
        }
    }

}
