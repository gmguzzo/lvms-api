package br.com.louvemos.api.setlist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.ServiceUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.person.PersonService;
import br.com.louvemos.api.song.Song;
import br.com.louvemos.api.song.SongService;
import br.com.louvemos.api.songsetlist.SongSetlist;
import br.com.louvemos.api.songsetlist.SongSetlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

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
    private PersonService personService;

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
            List<Long> pIds,
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

        List<Setlist> cList = setlistRepository.list(ids, pIds, q, names, firstResult, maxResults, sortWithDbKeys);

        return cList;
    }

    public Setlist create(Setlist setlist, Person p, List<Song> songs) throws LvmsException {
        if (p != null) {
            Person pPersist = personService.load(p.getId(), null);
            setlist.setPerson(pPersist);
        }

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

        List<SongSetlist> ssl = c.getSongSetlists();
        if (ssl != null) {
            for (SongSetlist ss : ssl) {
                songSetlistService.delete(ss.getId(), null, null);
            }
        }

        setlistRepository.delete(c);
    }

}
