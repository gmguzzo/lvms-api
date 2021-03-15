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

    public void validatePersist(SongCategory sc) throws LvmsException {
        validateNull(sc);

        validateSong(sc);
        validateCategory(sc);
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

    private void validateSong(SongCategory sc) throws LvmsException {
        if (sc.getSong() == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateCategory(SongCategory sc) throws LvmsException {
        if (sc.getCategory() == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

}
