package br.com.louvemos.api.songsetlist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.song.*;
import br.com.louvemos.api.setlist.*;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SongSetlistService {

    @Autowired
    private SetlistServiceValidator setlistServiceValidator;

    @Autowired
    private SetlistService setlistService;

    @Autowired
    private SongServiceValidator songServiceValidator;

    @Autowired
    private SongService songService;

    @Autowired
    private SongSetlistRepository songSetlistRepository;

    @Autowired
    private SongSetlistServiceValidator songSetlistServiceValidator;

    public SongSetlist load(Long id) {
        return songSetlistRepository.loadById(id);
    }

    public SongSetlist create(SongSetlist ss, Song song, Setlist s) throws LvmsException {
        Song songPersist = songService.load(song.getId());
        songServiceValidator.validateSongFound(songPersist);

        Setlist sPersist = setlistService.load(s.getId());
        setlistServiceValidator.validateSetlistFound(sPersist);

        ss.setSong(songPersist);
        ss.setSetlist(sPersist);

        ss.setUpTimestamps();

        songSetlistServiceValidator.validatePersist(ss);

        return songSetlistRepository.save(ss);
    }

    public void delete(Long ssId) throws LvmsException {
        SongSetlist ss = songSetlistRepository.loadById(ssId);
        songSetlistServiceValidator.validateSongSetlistFound(ss);

        songSetlistRepository.delete(ss);
    }

}
