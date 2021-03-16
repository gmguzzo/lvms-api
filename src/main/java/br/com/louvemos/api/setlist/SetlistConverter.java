package br.com.louvemos.api.setlist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class SetlistConverter {

    public Setlist toModel(Long id, SetlistDTO sd) {
        if (sd == null) {
            if (id == null) {
                return null;
            }
            return new Setlist(id);
        }

        Setlist s = new Setlist();

        s.setId(id);
        s.setName(sd.getName());
        s.setDescription(sd.getDescription());
        s.setPublic(sd.getIsPublic());

        return s;
    }

    public SetlistDTO toDTO(Setlist s) {
        if (s == null) {
            return null;
        }

        SetlistDTO sd = new SetlistDTO();
        sd.setId(s.getId());
        sd.setName(s.getName());
        sd.setDescription(s.getDescription());
        sd.setIsPublic(s.isPublic());

        return sd;
    }

}
