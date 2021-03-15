package br.com.louvemos.api.category;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;

import org.springframework.stereotype.Component;

@Component
public class CategoryControllerValidator {

    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        CategoryDTO cd = bdIn.getCategory();

        validateCategory(cd);
        validateCategoryName(cd);
        validateDescription(cd);

    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        CategoryDTO cd = bdIn.getCategory();

        validateCategory(cd);
        validateCategoryName(cd);
        validateDescription(cd);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

    private void validateCategory(CategoryDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

    private void validateCategoryName(CategoryDTO cd) throws LvmsException {
        if (StringUtils.isBlank(cd.getCategoryName())) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NAME_INVALID);
        }
    }

    private void validateDescription(CategoryDTO cd) throws LvmsException {
        if (cd.getDescription() != null && StringUtils.isBlank(cd.getDescription())) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_DESCRIPTION_INVALID);
        }
    }

}
