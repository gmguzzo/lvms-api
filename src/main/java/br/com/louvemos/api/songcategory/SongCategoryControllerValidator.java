package br.com.louvemos.api.songcategory;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import org.springframework.stereotype.Component;

@Component
public class SongCategoryControllerValidator {

    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        SongCategoryDTO sc = bdIn.getSongCategory();

        validateSongCategory(sc);
        validateSong(sc);
        validateCategory(sc);
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        SongCategoryDTO sc = bdIn.getSongCategory();

        validateSongCategory(sc);
        validateSong(sc);
        validateCategory(sc);
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

    private void validateSongCategory(SongCategoryDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

    private void validateSong(SongCategoryDTO sc) throws LvmsException {
        if (sc.getSong() == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateCategory(SongCategoryDTO sc) throws LvmsException {
        if (sc.getCategory() == null) {
            throw new LvmsException(LvmsCodesEnum.CATEGORY_NULL);
        }
    }

}
