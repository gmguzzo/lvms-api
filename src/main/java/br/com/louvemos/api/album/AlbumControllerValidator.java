package br.com.louvemos.api.album;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.base.BaseDTO;
import org.springframework.stereotype.Component;

@Component
public class AlbumControllerValidator {

//    void validateList(String ids, String symbols, String qSymbol, Integer firstResult, Integer maxResults, String sort) {
//    }
    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);

        AlbumDTO s = bdIn.getAlbum();

        validateAlbum(s);
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
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

    private void validateAlbum(AlbumDTO c) throws LvmsException {
        if (c == null) {
            throw new LvmsException(LvmsCodesEnum.ALBUM_NULL);
        }
    }

}
