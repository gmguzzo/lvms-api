/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class ExternalLinkControllerValidator {

    @Autowired
    ExternalLinkService externalLinkService;

    void validateCreate(BaseDTO bdIn) throws LvmsException {
        validateBaseDTO(bdIn);
        validateExternalLink(bdIn.getExternalLink());
        validateType(bdIn);
        validateMedia(bdIn);
        validateURL(bdIn);
    }

    void validateDelete(Long id) throws LvmsException {
        validateId(id);
    }

    private void validateId(Long externalLink) throws LvmsException {
        if (externalLink == null) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_NOT_FOUND);
        }
    }

    private void validateBaseDTO(BaseDTO bdIn) throws LvmsException {
        if (bdIn == null) {
            throw new LvmsException(LvmsCodesEnum.JSON_INVALID_FORMAT);
        }
    }

    private void validateExternalLink(ExternalLinkDTO externalLink) throws LvmsException {
        if (externalLink == null) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_NOT_FOUND);
        }
    }

    // PRIVATE METHODS
    private void validateType(BaseDTO bdIn) throws LvmsException {
        if (StringUtils.isBlank(bdIn.getExternalLink().getType())) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_TYPE_INVALID);
        }
    }

    private void validateMedia(BaseDTO bdIn) throws LvmsException {
        if (StringUtils.isBlank(bdIn.getExternalLink().getMedia())) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_MEDIA_INVALID);
        }
    }

    private void validateURL(BaseDTO bdIn) throws LvmsException {
        if (StringUtils.isBlank(bdIn.getExternalLink().getUrl())) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_URL_INVALID);
        }
    }

}
