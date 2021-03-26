package br.com.louvemos.api.songcategory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.song.*;
import br.com.louvemos.api.category.*;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SongCategoryService {

    @Autowired
    private CategoryServiceValidator categoryServiceValidator;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SongServiceValidator songServiceValidator;

    @Autowired
    private SongService songService;

    @Autowired
    private SongCategoryServiceValidator songCategoryServiceValidator;

    @Autowired
    private SongCategoryRepository songCategoryRepository;

    public SongCategory update(SongCategory sc) throws LvmsException {
        SongCategory scPersist = load(sc.getId(), null, null);
        songCategoryServiceValidator.validateSongCategoryFound(scPersist);

        scPersist.setMain(sc.isMain());

        scPersist.setUpTimestamps();
        songCategoryServiceValidator.validatePersist(scPersist);

        return songCategoryRepository.update(scPersist);

    }

    public SongCategory load(Long id, Long songId, Long categoryId) {
        if (id != null) {
            return songCategoryRepository.loadById(id);
        } else {
            return songCategoryRepository.loadBySongAndCategory(songId, categoryId);
        }
    }

    public SongCategory create(SongCategory sc, Song song, Category c) throws LvmsException {
        Song sPersist = songService.load(song.getId());
        songServiceValidator.validateSongFound(sPersist);

        Category cPersist = categoryService.load(c.getId(), c.getCategoryName());
        categoryServiceValidator.validateCategoryFound(cPersist);

        sc.setSong(sPersist);
        sc.setCategory(cPersist);

        sc.setUpTimestamps();

        songCategoryServiceValidator.validatePersist(sc);

        return songCategoryRepository.save(sc);
    }

    public void delete(Long scId, Long songId, Long categoryId) throws LvmsException {
        SongCategory sc = this.load(scId, songId, categoryId);
        songCategoryServiceValidator.validateSongCategoryFound(sc);

        songCategoryRepository.delete(sc);
    }

}
