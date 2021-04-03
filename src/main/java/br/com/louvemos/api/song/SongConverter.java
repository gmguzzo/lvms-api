package br.com.louvemos.api.song;

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
public class SongConverter {

    @Autowired
    private PersonShareService personShareService;

    @Autowired
    private PersonService personService;

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
        s.setTone(sd.getTone());
        s.setStatus(sd.getStatus());
        s.setPublic(sd.getIsPublic() != null ? sd.getIsPublic() : false);

        return s;
    }

    public SongDTO toDTO(Song s) {
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
                    PersonShareSubjectTypeEnum.SONG,
                    s.getId(),
                    pPersist.getId()) != null;
            if (isSharedWith) {
                perm = true;
            }
        }

        if (!perm) {
            return null;
        }

        SongDTO sd = new SongDTO();
        sd.setId(s.getId());
        sd.setTitle(s.getTitle());
        sd.setDescription(s.getDescription());
        sd.setLyric(s.getLyric());
        sd.setTone(s.getTone());
        sd.setStatus(s.getStatus());

        return sd;
    }

}
