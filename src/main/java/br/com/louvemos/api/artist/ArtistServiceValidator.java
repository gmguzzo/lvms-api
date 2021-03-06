package br.com.louvemos.api.artist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

@Component
public class ArtistServiceValidator {

    public void validatePersist(Artist a) throws LvmsException {
        validateNull(a);
    }

    public void validateArtistFound(Artist a) throws LvmsException {
        if (a == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

    /*
     *   PRIVATE METHODS
     */
    private void validateNull(Artist a) throws LvmsException {
        if (a == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

}
