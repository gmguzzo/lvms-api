package br.com.louvemos.api.setlist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.album.Album;
import br.com.louvemos.api.base.ServiceUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.song.*;
import br.com.louvemos.api.songsetlist.SongSetlist;
import br.com.louvemos.api.songsetlist.SongSetlistService;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SetlistService {

    @Autowired
    private SetlistServiceValidator setlistServiceValidator;

    @Autowired
    private SetlistRepository setlistRepository;

    @Autowired
    private SongService songService;

    @Autowired
    private SongSetlistService songSetlistService;

    public Setlist update(Setlist s) throws LvmsException {
        Setlist sPersist = load(s.getId());
        setlistServiceValidator.validateSetlistFound(sPersist);

        sPersist.setName(s.getName());
        sPersist.setDescription(s.getDescription());
        sPersist.setPublic(s.isPublic());

        sPersist.setUpTimestamps();
        setlistServiceValidator.validatePersist(sPersist);

        return setlistRepository.update(sPersist);

    }

    public Setlist load(Long id) {
        return setlistRepository.loadById(id);
    }

    public List<Setlist> list(
            List<Long> ids,
            String q,
            List<String> names,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = ServiceUtils.convertSortMapToDbKeys(
                sortMap,
                "s.id",
                SortDirectionEnum.desc,
                (apiKey, apiValue) -> {
                    switch (apiKey.toLowerCase()) {
                        default:
                            return "";
                    }
                });

        List<Setlist> cList = setlistRepository.list(ids, q, names, firstResult, maxResults, sortWithDbKeys);

        return cList;
    }

    public Setlist create(Setlist setlist, Album album, List<Song> songs) throws LvmsException {
        setlist.setUpTimestamps();

        setlistServiceValidator.validatePersist(setlist);

        Setlist sPersist = setlistRepository.save(setlist);

        if (songs != null && !songs.isEmpty()) {
            for (Song s : songs) {
                songService.load(s.getId());
                songSetlistService.create(new SongSetlist(), s, sPersist);
            }
        }

        return sPersist;
    }

    public void delete(Long cId) throws LvmsException {
        Setlist c = setlistRepository.loadById(cId);
        setlistServiceValidator.validateSetlistFound(c);

        setlistRepository.delete(c);
    }

}
