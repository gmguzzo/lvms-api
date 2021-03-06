package br.com.louvemos.api.song;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.album.Album;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SongService {

    @Autowired
    private SongServiceValidator songServiceValidator;

    @Autowired
    private SongRepository songRepository;

    public Song update(Song s) throws LvmsException {
        Song sPersist = load(s.getId());
        songServiceValidator.validateSongFound(sPersist);

        sPersist.setTitle(s.getTitle());
        sPersist.setDescription(s.getDescription());
        sPersist.setLyric(s.getLyric());

        sPersist.setUpTimestamps();
        songServiceValidator.validatePersist(sPersist);

        return songRepository.update(sPersist);

    }

    public Song load(Long id) {
        return songRepository.loadById(id);
    }

//    public List<Song> list(
//            String qSymbol,
//            List<Long> cIdList,
//            List<String> symbolList,
//            Integer firstResult,
//            Integer maxResults,
//            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {
//
//        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = ServiceUtils.convertSortMapToDbKeys(
//                sortMap,
//                "c.id",
//                SortDirectionEnum.desc,
//                (apiKey, apiValue) -> {
//                    switch (apiKey.toLowerCase()) {
//                        default:
//                            return "";
//                    }
//                });
//
//        List<Song> cList = songRepository.list(qSymbol, cIdList, symbolList, firstResult, maxResults, sortWithDbKeys);
//
//        return cList;
//    }
    public Song create(Song song, Album album) throws LvmsException {
        song.setUpTimestamps();

        songServiceValidator.validatePersist(song);

        return songRepository.save(song);
    }

    public void delete(Long cId) throws LvmsException {
        Song c = songRepository.loadById(cId);
        songServiceValidator.validateSongFound(c);

        songRepository.delete(c);
    }

}