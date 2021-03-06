package br.com.louvemos.api.song;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.chord.*;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

@Component
public class SongServiceValidator {

    public void validatePersist(Song c) throws LvmsException {
        validateNull(c);
    }

    public void validateSongFound(Song c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

    /*
     *   PRIVATE METHODS
     */
    private void validateNull(Song c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.SONG_NULL);
        }
    }

}
