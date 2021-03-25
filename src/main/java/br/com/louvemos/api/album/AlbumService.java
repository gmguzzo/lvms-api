package br.com.louvemos.api.album;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.louvemos.api.artist.Artist;
import br.com.louvemos.api.artist.ArtistService;
import br.com.louvemos.api.artist.ArtistServiceValidator;
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
public class AlbumService {

    @Autowired
    private ArtistServiceValidator artistServiceValidator;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumServiceValidator albumServiceValidator;

    @Autowired
    private AlbumRepository albumRepository;

    public Album update(Album a) throws LvmsException {
        Album aPersist = load(a.getId(), null);
        albumServiceValidator.validateAlbumFound(aPersist);

        aPersist.setAlbumName(a.getAlbumName());
        aPersist.setDebut(a.getDebut());
        aPersist.setGenre(a.getGenre());

        aPersist.setUpTimestamps();
        albumServiceValidator.validatePersist(aPersist);

        return albumRepository.update(aPersist);

    }

    public Album load(Long id, String name) {
        if (id != null) {
            return albumRepository.loadById(id);
        } else {
            return albumRepository.loadByName(name);
        }
    }

    public List<Album> list(
            String q,
            List<Long> aIdList,
            List<String> names,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = ServiceUtils.convertSortMapToDbKeys(
                sortMap,
                "a.album_name",
                SortDirectionEnum.asc,
                (apiKey, apiValue) -> {
                    switch (apiKey.toLowerCase()) {
                        default:
                            return "";
                    }
                });

        return albumRepository.list(q, aIdList, names, firstResult, maxResults, sortWithDbKeys);
    }

    public Album create(Album album, Artist artist) throws LvmsException {
        Artist aPersist = artistService.load(artist.getId(), artist.getArtistName());
        if (aPersist == null) {
            aPersist = artistService.create(artist);
        }

        artistServiceValidator.validateArtistFound(aPersist);

        album.setArtist(aPersist);
        album.setUpTimestamps();

        albumServiceValidator.validatePersist(album);

        return albumRepository.save(album);
    }

    public void delete(Long cId) throws LvmsException {
        Album c = albumRepository.loadById(cId);
        albumServiceValidator.validateAlbumFound(c);

        albumRepository.delete(c);
    }

}
