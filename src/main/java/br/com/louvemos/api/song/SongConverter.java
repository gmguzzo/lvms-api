package br.com.louvemos.api.song;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class SongConverter {

    public Song toModel(Long id, SongDTO sd) {
        if (sd == null) {
            if (id == null) {
                return null;
            }
            return new Song(id);
        }

        Song s = new Song();

        s.setId(id);
        s.setTitle(sd.getTitle());
        s.setDescription(sd.getDescription());
        s.setLyric(sd.getLyric());

        return s;
    }

    public SongDTO toDTO(Song s) {
        if (s == null) {
            return null;
        }

        SongDTO sd = new SongDTO();
        sd.setId(s.getId());
        sd.setTitle(s.getTitle());
        sd.setDescription(s.getDescription());
        sd.setLyric(s.getLyric());

        return sd;
    }

}
