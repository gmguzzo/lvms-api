package br.com.louvemos.api.songcategory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class SongCategoryConverter {

    public SongCategory toModel(Long id, SongCategoryDTO scd) {
        if (scd == null) {
            if (id == null) {
                return null;
            }
            return new SongCategory(id);
        }

        SongCategory sc = new SongCategory();

        sc.setId(id);
        sc.setMain(scd.getMain());

        return sc;
    }

    public SongCategoryDTO toDTO(SongCategory sc) {
        if (sc == null) {
            return null;
        }

        SongCategoryDTO scd = new SongCategoryDTO();
        scd.setId(sc.getId());
        scd.setMain(sc.isMain());

        return scd;
    }

}
