package br.com.louvemos.api.artist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class ArtistConverter {

    public Artist toModel(Long id, ArtistDTO ad) {
        if (ad == null) {
            if (id == null) {
                return null;
            }
            return new Artist(id);
        }

        Artist a = new Artist();

        a.setId(id);
        a.setArtistName(ad.getArtistName());
        a.setGenre(ad.getGenre());
        a.setSince(ad.getSince());

        return a;
    }

    public ArtistDTO toDTO(Artist a) {
        if (a == null) {
            return null;
        }

        ArtistDTO ad = new ArtistDTO();
        ad.setId(a.getId());
        ad.setArtistName(a.getArtistName());
        ad.setGenre(a.getGenre());
        ad.setSince(a.getSince());

        return ad;
    }

}
