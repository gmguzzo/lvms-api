package br.com.louvemos.api.artist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

@Component
public class ArtistServiceValidator {

    public void validatePersist(Artist a) throws LvmsException {
        validateNull(a);
        validateArtistName(a);
        validateGenre(a);
        validateSince(a);
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

    private void validateArtistName(Artist a) throws LvmsException {
        if (StringUtils.isBlank(a.getArtistName())) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_NAME_INVALID);
        }
    }

    private void validateGenre(Artist a) throws LvmsException {
        if (a.getGenre() != null && StringUtils.isBlank(a.getGenre())) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_GENRE_INVALID);
        }
    }

    private void validateSince(Artist a) throws LvmsException {
        if (a.getSince() != null && StringUtils.isBlank(a.getSince())) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_SINCE_INVALID);
        }
    }

}
