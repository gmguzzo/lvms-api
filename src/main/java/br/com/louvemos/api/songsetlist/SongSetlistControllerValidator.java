/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.songsetlist;

import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class SongSetlistControllerValidator {

    public void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        validateSongSetlist(bdIn);
        validateSong(bdIn.getSongSetlist());
        validateSetlist(bdIn.getSongSetlist());
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

    private void validateSong(SongSetlistDTO ss) throws LvmsException {
        if (ss.getSong() == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    private void validateSetlist(SongSetlistDTO ss) throws LvmsException {
        if (ss.getSetlist() == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NULL);
        }
    }

    private void validateSongSetlist(BaseDTO bdIn) throws LvmsException {
        if (bdIn.getSongSetlist() == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NULL);
        }
    }

}
