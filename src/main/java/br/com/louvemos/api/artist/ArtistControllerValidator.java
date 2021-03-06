package br.com.louvemos.api.artist;

import br.com.louvemos.api.artist.*;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import org.springframework.stereotype.Component;

@Component
public class ArtistControllerValidator {

//    void validateList(String ids, String symbols, String qSymbol, Integer firstResult, Integer maxResults, String sort) {
//    }
    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        ArtistDTO s = bdIn.getArtist();

        validateArtist(s);
    }

    public void validateUpdate(Long id, BaseDTO bdIn) throws LvmsException {
        validateId(id);
        validateBaseDTO(bdIn);

        ArtistDTO c = bdIn.getArtist();

        validateArtist(c);
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
            throw new LvmsException(LvmsCodesEnum.ARTIST_NULL);
        }
    }

    private void validateArtist(ArtistDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.ARTIST_NULL);
        }
    }

}
