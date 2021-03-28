/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import org.springframework.stereotype.Component;

/**
 *
 * @author heits
 */
@Component
public class ExternalLinkConverter {

    public ExternalLink toModel(Long id, ExternalLinkDTO elj) {
        if (elj == null) {
            if (id == null) {
                return null;
            }
            return new ExternalLink(id);
        }

        ExternalLink externalLink = new ExternalLink();
        externalLink.setId(id);
        externalLink.setType(ExternalLinkTypeEnum.valueOf(elj.getType()));
        externalLink.setMedia(ExternalLinkMediaEnum.valueOf(elj.getMedia()));
        externalLink.setUrl(elj.getUrl());
        return externalLink;
    }

    public ExternalLinkDTO toDTO(ExternalLink el) {
        if (el == null) {
            return null;
        }

        ExternalLinkDTO elj = new ExternalLinkDTO();
        elj.setId(el.getId());
        elj.setType(el.getType().toString());
        elj.setMedia(el.getMedia().toString());
        elj.setUrl(el.getUrl());
        return elj;
    }

}
