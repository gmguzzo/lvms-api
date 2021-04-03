/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.artist;

import br.com.louvemos.api.album.Album;
import br.com.louvemos.api.album.AlbumConverter;
import br.com.louvemos.api.album.AlbumDTO;
import br.com.louvemos.api.auth.MyUserDetails;
import br.com.louvemos.api.base.*;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author gmguzzo
 */
@Controller
@RequestMapping(value = "/v2/artists")
public class ArtistController extends BaseController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistControllerValidator artistControllerValidator;

    @Autowired
    private ArtistConverter artistConverter;

    @Autowired
    private AlbumConverter albumConverter;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO list(
            @RequestParam(required = false, value = "ids") String ids,
            @RequestParam(required = false, value = "q") String q,
            @RequestParam(required = false, value = "names") String names,
            @RequestParam(required = false, value = "isPublic") Boolean isPublic,
            @RequestParam(required = false, value = "firstResult") Integer firstResult,
            @RequestParam(required = false, value = "maxResults") Integer maxResults,
            @RequestParam(required = false, value = "sort") String sort
    ) throws LvmsException {

        List<Long> idList = ControllerUtils.parseCSVToLongList(ids);
        List<String> nameList = ControllerUtils.parseCSVToStringList(names);

        firstResult = ControllerUtils.adjustFirstResult(firstResult);
        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);

        List<Artist> list = artistService.list(q, idList, nameList, isPublic, firstResult, maxResults, sortMap);

        List<ArtistDTO> adList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Artist a : list) {
                ArtistDTO ard = artistConverter.toDTO(a);
                if (a.getAlbums() != null && !a.getAlbums().isEmpty()) {
                    List<AlbumDTO> albums = new ArrayList<>();
                    for (Album al : a.getAlbums()) {
                        albums.add(albumConverter.toDTO(al));
                    }
                    ard.setAlbums(albums);
                }
                adList.add(ard);
            }
        }

        BaseDTO bd = new BaseDTO();
        bd.setArtists(adList);

        return bd;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO create(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        artistControllerValidator.validateCreate(bdIn);

        // Convert
        Artist a = artistConverter.toModel(null, bdIn.getArtist());

        Person p = null;
        if (!a.isPublic()) {
            MyUserDetails authDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            p = personService.load(null, authDetails.getUsername());
        }
        
        // Create
        Artist aPersist = artistService.create(a, p);


        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setArtist(artistConverter.toDTO(aPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO update(
            @PathVariable(value = "id") Long id,
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        artistControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Artist s = artistConverter.toModel(id, bdIn.getArtist());

        // Update
        Artist sPersist = artistService.update(s);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setArtist(artistConverter.toDTO(sPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        artistControllerValidator.validateDelete(id);

        //Delete
        artistService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
