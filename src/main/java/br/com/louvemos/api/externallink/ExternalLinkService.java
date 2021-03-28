/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import br.com.louvemos.api.song.Song;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.song.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExternalLinkService {

    @Autowired
    private SongService songService;

    @Autowired
    private ExternalLinkRepository externalLinkRepository;

    @Autowired
    private ExternalLinkServiceValidator externalLinkServiceValidator;

    public void delete(Long externalLinkId) throws LvmsException {
        ExternalLink externalLink = externalLinkRepository.loadById(externalLinkId);
        externalLinkServiceValidator.validateDelete(externalLink);
        externalLinkRepository.delete(externalLink);
    }

    public ExternalLink load(Long id) {
        return externalLinkRepository.loadById(id);
    }

    public ExternalLink create(ExternalLink externalLink, Song s) throws LvmsException {
        Song sPersist = songService.load(s.getId());
        externalLink.setSong(sPersist);
        externalLink.setUpTimestamps();

        externalLinkServiceValidator.validatePersistCreate(externalLink);

        externalLink = externalLinkRepository.save(externalLink);

        return externalLink;
    }

}
