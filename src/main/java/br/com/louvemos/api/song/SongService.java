package br.com.louvemos.api.song;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.album.Album;
import br.com.louvemos.api.album.AlbumService;
import br.com.louvemos.api.album.AlbumServiceValidator;
import br.com.louvemos.api.artist.Artist;
import br.com.louvemos.api.base.ServiceUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.category.Category;
import br.com.louvemos.api.category.CategoryService;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.songcategory.SongCategory;
import br.com.louvemos.api.songcategory.SongCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.Hibernate;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SongService {

    @Autowired
    private AlbumServiceValidator albumServiceValidator;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SongCategoryService songCategoryService;

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
        Song sPersist = songRepository.loadById(id);

        Hibernate.initialize(sPersist);
        Hibernate.initialize(sPersist.getSongSetlists());

        return sPersist;
    }

    public List<Song> list(
            List<Long> ids,
            List<Long> albumIds,
            List<Long> artistIds,
            List<Long> setlistIds,
            String q,
            List<String> categoryList,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = ServiceUtils.convertSortMapToDbKeys(
                sortMap,
                "s.title",
                SortDirectionEnum.asc,
                (apiKey, apiValue) -> {
                    switch (apiKey.toLowerCase()) {
                        default:
                            return "";
                    }
                });

        List<Song> sList = songRepository.list(ids, albumIds, artistIds, setlistIds, q, categoryList, firstResult, maxResults, sortWithDbKeys);

        for (Song song : sList) {
            Hibernate.initialize(song.getExternalLinks());
        }

        return sList;
    }

    public Song create(Song song, Album album, Artist ar, List<Category> categories) throws LvmsException {
        Album aPersist = albumService.load(album.getId(), album.getAlbumName());
        if (aPersist == null) {
            aPersist = albumService.create(album, ar);
        }
        albumServiceValidator.validateAlbumFound(aPersist);
        song.setAlbum(aPersist);
        song.setUpTimestamps();

        songServiceValidator.validatePersist(song);

        Song sPersist = songRepository.save(song);

        if (categories != null && !categories.isEmpty()) {
            for (Category c : categories) {
                Category cPersist = categoryService.loadOrCreate(c);
                songCategoryService.create(new SongCategory(), sPersist, cPersist);
            }
        }

        return sPersist;
    }

    public void delete(Long sId) throws LvmsException {
        Song s = songRepository.loadById(sId);
        songServiceValidator.validateSongFound(s);

        songRepository.delete(s);
    }

}
