package br.com.louvemos.api.setlist;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SetlistControllerValidator {

    public void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        SetlistDTO s = bdIn.getSetlist();

        validateSetlist(s);
        validateName(s);
        validateDescription(s);
        validatePublic(s);
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        SetlistDTO sd = bdIn.getSetlist();

        validateSetlist(sd);
        validateName(sd);
        validateDescription(sd);
        validatePublic(sd);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.JSON_INVALID_FORMAT);
        }
    }

    private void validateSetlist(SetlistDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NULL);
        }
    }

    private void validatePublic(SetlistDTO s) throws LvmsException {
        if (s.getIsPublic() == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_PUBLIC_INVALID);
        }
    }

    private void validateDescription(SetlistDTO s) throws LvmsException {
        if (s.getDescription() != null && StringUtils.isBlank(s.getDescription())) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_DESCRIPTION_INVALID);
        }
    }

    private void validateName(SetlistDTO s) throws LvmsException {
        if (StringUtils.isBlank(s.getName())) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NAME_INVALID);
        }
    }

}
