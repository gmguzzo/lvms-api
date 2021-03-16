/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.songsetlist;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class SongSetlistServiceValidator {

    public void validatePersist(SongSetlist ss) {
    }

    public void validateSongSetlistFound(SongSetlist ss) throws LvmsException {
        validateNull(ss);
    }

    private void validateNull(SongSetlist ss) throws LvmsException {
        if (ss == null) {
            throw new LvmsException(LvmsCodesEnum.SETLIST_NULL);
        }
    }

}
