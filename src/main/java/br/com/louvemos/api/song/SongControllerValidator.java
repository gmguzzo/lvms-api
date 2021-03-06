package br.com.louvemos.api.song;

import br.com.louvemos.api.song.*;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SongControllerValidator {

//    void validateList(String ids, String symbols, String qSymbol, Integer firstResult, Integer maxResults, String sort) {
//    }

    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        SongDTO s = bdIn.getSong();

        validateSong(s);
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        SongDTO c = bdIn.getSong();

        validateSong(c);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateSong(SongDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

}
