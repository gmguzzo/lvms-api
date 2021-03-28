/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.stereotype.Component;

/**
 *
 * @author heits
 */
@Component
public class ExternalLinkServiceValidator {

    public void validatePersistCreate(ExternalLink externalLink) throws LvmsException {
        validateNull(externalLink);
        validateType(externalLink);
        validateMedia(externalLink);
        validateSong(externalLink);
    }

    public void validateDelete(ExternalLink el) throws LvmsException {
        validateNull(el);
    }

    public void validateNull(ExternalLink externalLink) throws LvmsException {
        if (externalLink == null) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_NOT_FOUND);
        }
    }

    private void validateType(ExternalLink externalLink) throws LvmsException {
        if (externalLink.getType() == null) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_TYPE_INVALID);
        }
    }

    private void validateMedia(ExternalLink externalLink) throws LvmsException {
        if (externalLink.getMedia() == null) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_MEDIA_INVALID);
        }
    }

    private void validateSong(ExternalLink externalLink) throws LvmsException {
        if (externalLink.getSong() == null || externalLink.getSong().getId() == null) {
            throw new LvmsException(LvmsCodesEnum.EXTERNAL_LINK_NOT_FOUND);
        }
    }

}
