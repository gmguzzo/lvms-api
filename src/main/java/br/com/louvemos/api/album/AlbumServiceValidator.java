package br.com.louvemos.api.album;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

@Component
public class AlbumServiceValidator {

    public void validatePersist(Album a) throws LvmsException {
        validateNull(a);
    }

    public void validateAlbumFound(Album a) throws LvmsException {
        if (a == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

    /*
     *   PRIVATE METHODS
     */
    private void validateNull(Album a) throws LvmsException {
        if (a == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

}
