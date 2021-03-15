package br.com.louvemos.api.artist;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ArtistControllerValidator {

    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        ArtistDTO ad = bdIn.getArtist();

        validateArtist(ad);
        validateArtistName(ad);
        validateGenre(ad);
        validateSince(ad);
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        ArtistDTO ad = bdIn.getArtist();

        validateArtist(ad);
        validateArtistName(ad);
        validateGenre(ad);
        validateSince(ad);
    }

    public void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long id) throws LvmsException {
        if (id == null) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_NULL);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.JSON_INVALID_FORMAT);
        }
    }

    private void validateArtist(ArtistDTO ad) throws LvmsException {
        if (ad == null) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_NULL);
        }
    }

    private void validateArtistName(ArtistDTO ad) throws LvmsException {
        if (StringUtils.isBlank(ad.getArtistName())) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_NAME_INVALID);
        }
    }

    private void validateGenre(ArtistDTO ad) throws LvmsException {
        if (ad.getGenre() != null && StringUtils.isBlank(ad.getGenre())) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_GENRE_INVALID);
        }
    }

    private void validateSince(ArtistDTO ad) throws LvmsException {
        if (ad.getSince() != null && StringUtils.isBlank(ad.getSince())) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_SINCE_INVALID);
        }
    }

}
