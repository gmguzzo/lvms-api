package br.com.louvemos.api.artist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.base.ServiceUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ArtistService {

    @Autowired
    private ArtistServiceValidator artistServiceValidator;

    @Autowired
    private ArtistRepository artistRepository;

    public Artist update(Artist a) throws LvmsException {
        Artist aPersist = load(a.getId(), null);
        artistServiceValidator.validateArtistFound(aPersist);

        aPersist.setArtistName(a.getArtistName());
        aPersist.setGenre(a.getGenre());
        aPersist.setSince(a.getSince());

        aPersist.setUpTimestamps();
        artistServiceValidator.validatePersist(aPersist);

        return artistRepository.update(aPersist);

    }

    public Artist load(Long id, String name) {
        if (id != null) {
            return artistRepository.loadById(id);
        } else {
            return artistRepository.loadByName(name);
        }
    }

    public List<Artist> list(
            String q, List<Long> aIdList, List<String> names, Boolean isPublic, Integer firstResult, Integer maxResults, LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = ServiceUtils.convertSortMapToDbKeys(
                sortMap,
                "a.artist_name",
                SortDirectionEnum.asc,
                (apiKey, apiValue) -> {
                    switch (apiKey.toLowerCase()) {
                        default:
                            return "";
                    }
                });

        return artistRepository.list(q, aIdList, names, isPublic, firstResult, maxResults, sortWithDbKeys);
    }

    public Artist create(Artist artist, Person p) throws LvmsException {
        artist.setPerson(p);
        artist.setUpTimestamps();

        artistServiceValidator.validatePersist(artist);

        return artistRepository.save(artist);
    }

    public void delete(Long cId) throws LvmsException {
        Artist c = artistRepository.loadById(cId);
        artistServiceValidator.validateArtistFound(c);

        artistRepository.delete(c);
    }

}
