package br.com.louvemos.api.artist;

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
public class ArtistConverter {

    @Autowired
    private PersonService personService;

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

    public ArtistDTO toDTO(Artist a, boolean hasRecursivePermission) {
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

        ArtistDTO ad = new ArtistDTO();
        ad.setId(a.getId());
        ad.setArtistName(a.getArtistName());
        ad.setGenre(a.getGenre());
        ad.setSince(a.getSince());

        return ad;
    }

}
