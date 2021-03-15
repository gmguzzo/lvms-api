package br.com.louvemos.api.album;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.artist.Artist;
import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

@Component
public class AlbumServiceValidator {

    public void validatePersist(Album a) throws LvmsException {
        validateNull(a);
        validateAlbumName(a);
        validateGenre(a);
        validateDebut(a);
        validateArtist(a.getArtist());
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

    private void validateArtist(Artist a) throws LvmsException {
        if (a == null) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_NULL);
        }
    }

    private void validateAlbumName(Album a) throws LvmsException {
        if (StringUtils.isBlank(a.getAlbumName())) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NAME_INVALID);
        }
    }

    private void validateGenre(Album a) throws LvmsException {
        if (a.getGenre() != null && StringUtils.isBlank(a.getGenre())) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_GENRE_INVALID);
        }
    }

    private void validateDebut(Album a) throws LvmsException {
        if (a.getDebut() != null && StringUtils.isBlank(a.getDebut())) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_DEBUT_INVALID);
        }
    }

}
