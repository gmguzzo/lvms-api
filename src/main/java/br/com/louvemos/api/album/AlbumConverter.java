package br.com.louvemos.api.album;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.auth.MyUserDetails;
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AlbumConverter {

    @Autowired
    private PersonService personService;

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
        a.setPublic(ad.getIsPublic() != null ? ad.getIsPublic() : false);

        return a;
    }

    public AlbumDTO toDTO(Album a, boolean hasRecursivePermission) {
        if (a == null) {
            return null;
        }

        boolean perm = true;
        MyUserDetails authDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person pPersist = personService.load(null, authDetails.getUsername());

        if (!a.isPublic()) {
            perm = false;
            if (a.getPerson().getUsername().equals(pPersist.getUsername())) {
                perm = true;
            }
        }

        if (!perm && !hasRecursivePermission) {
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
