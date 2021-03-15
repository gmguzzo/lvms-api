package br.com.louvemos.api.category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryServiceValidator {

    public void validatePersist(Category c) throws LvmsException {
        validateNull(c);
    }

    public void validateCategoryFound(Category c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

    /*
     *   PRIVATE METHODS
     */
    private void validateNull(Category c) throws LvmsException {
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
