package br.com.louvemos.api.album;

import br.com.louvemos.api.artist.ArtistDTO;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AlbumControllerValidator {

    public void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        AlbumDTO ad = bdIn.getAlbum();

        validateAlbum(ad);
        validateAlbumName(ad);
        validateGenre(ad);
        validateDebut(ad);
        validateArtist(ad.getArtist());
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        AlbumDTO c = bdIn.getAlbum();

        validateAlbum(c);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.JSON_INVALID_FORMAT);
        }
    }

    private void validateAlbum(AlbumDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

    private void validateArtist(ArtistDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_NULL);
        }
    }

    private void validateAlbumName(AlbumDTO ad) throws LvmsException {
        if (StringUtils.isBlank(ad.getAlbumName())) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NAME_INVALID);
        }
    }

    private void validateGenre(AlbumDTO ad) throws LvmsException {
        if (ad.getGenre() != null && StringUtils.isBlank(ad.getGenre())) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_GENRE_INVALID);
        }
    }

    private void validateDebut(AlbumDTO ad) throws LvmsException {
        if (ad.getDebut() != null && StringUtils.isBlank(ad.getDebut())) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_DEBUT_INVALID);
        }
    }

}
