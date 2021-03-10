package br.com.louvemos.api.songcategory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

@Component
public class SongCategoryServiceValidator {

    public void validatePersist(SongCategory c) throws LvmsException {
        validateNull(c);
    }

    public void validateSongCategoryFound(SongCategory c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

    /*
     *   PRIVATE METHODS
     */
    private void validateNull(SongCategory c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

}
