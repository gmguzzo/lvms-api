package br.com.louvemos.api.setlist;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.louvemos.api.auth.MyUserDetails;
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.person.PersonService;
import br.com.louvemos.api.personshare.PersonShareService;
import br.com.louvemos.api.personshare.PersonShareSubjectTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SetlistConverter {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonShareService personShareService;

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

        boolean perm = true;
        MyUserDetails authDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person pPersist = personService.load(null, authDetails.getUsername());

        if (!s.isPublic()) {
            perm = false;
            if (s.getPerson().getUsername().equals(pPersist.getUsername())) {
                perm = true;
            }

            boolean isSharedWith = personShareService.load(null,
                    PersonShareSubjectTypeEnum.SETLIST,
                    s.getId(),
                    pPersist.getId()) != null;
            if (isSharedWith) {
                perm = true;
            }
        }

        if (!perm) {
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
