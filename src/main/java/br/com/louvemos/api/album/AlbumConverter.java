package br.com.louvemos.api.album;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class AlbumConverter {

    public Album toModel(Long id, AlbumDTO ad) {
        if (ad == null) {
            if (id == null) {
                return null;
            }
            return new Album(id);
        }

        Album a = new Album();

        a.setId(id);
        a.setAlbumName(ad.getAlbumName());
        a.setDebut(ad.getDebut());
        a.setGenre(ad.getGenre());

        return a;
    }

    public AlbumDTO toDTO(Album a) {
        if (a == null) {
            return null;
        }

        AlbumDTO ad = new AlbumDTO();
        ad.setId(a.getId());
        ad.setAlbumName(a.getAlbumName());
        ad.setDebut(a.getDebut());
        ad.setGenre(a.getGenre());

        return ad;
    }

}
