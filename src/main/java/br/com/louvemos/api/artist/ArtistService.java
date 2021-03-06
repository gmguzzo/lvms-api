package br.com.louvemos.api.artist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ArtistService {

    @Autowired
    private ArtistServiceValidator artistServiceValidator;

    @Autowired
    private ArtistRepository artistRepository;

    public Artist update(Artist a) throws LvmsException {
        Artist aPersist = load(a.getId());
        artistServiceValidator.validateArtistFound(aPersist);

        aPersist.setArtistName(a.getArtistName());
        aPersist.setGenre(a.getGenre());
        aPersist.setSince(a.getSince());

        aPersist.setUpTimestamps();
        artistServiceValidator.validatePersist(aPersist);

        return artistRepository.update(aPersist);

    }

    public Artist load(Long id) {
        return artistRepository.loadById(id);
    }

//    public List<Artist> list(
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
//        List<Artist> cList = artistRepository.list(qSymbol, cIdList, symbolList, firstResult, maxResults, sortWithDbKeys);
//
//        return cList;
//    }
    public Artist create(Artist artist) throws LvmsException {
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
