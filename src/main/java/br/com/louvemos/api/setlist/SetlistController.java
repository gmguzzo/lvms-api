/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.setlist;

import br.com.louvemos.api.base.*;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.person.PersonConverter;
import br.com.louvemos.api.person.PersonDTO;
import br.com.louvemos.api.person.PersonService;
import br.com.louvemos.api.song.Song;
import br.com.louvemos.api.song.SongConverter;
import br.com.louvemos.api.song.SongDTO;
import br.com.louvemos.api.songsetlist.SongSetlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gmguzzo
 */
@Controller
@RequestMapping(value = "/v2/setlists")
public class SetlistController extends BaseController {

    @Autowired
    private SetlistService setlistService;

    @Autowired
    private SetlistControllerValidator setlistControllerValidator;

    @Autowired
    private SetlistConverter setlistConverter;

    @Autowired
    private SongConverter songConverter;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO list(
            @RequestParam(required = false, value = "ids") String ids,
            @RequestParam(required = false, value = "personIds") String pIds,
            @RequestParam(required = false, value = "q") String q,
            @RequestParam(required = false, value = "names") String names,
            @RequestParam(required = false, value = "firstResult") Integer firstResult,
            @RequestParam(required = false, value = "maxResults") Integer maxResults,
            @RequestParam(required = false, value = "sort") String sort
    ) throws LvmsException {

        List<Long> idList = ControllerUtils.parseCSVToLongList(ids);
        List<Long> pIdList = ControllerUtils.parseCSVToLongList(pIds);
        List<String> nameList = ControllerUtils.parseCSVToStringList(names);

        firstResult = ControllerUtils.adjustFirstResult(firstResult);
        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);

        List<Setlist> list = setlistService.list(idList, pIdList, q, nameList, firstResult, maxResults, sortMap);

        List<SetlistDTO> sdList = new ArrayList<>();
        for (Setlist s : list) {
            SetlistDTO sd = setlistConverter.toDTO(s);
            if (sd == null) {
                continue;
            }
            PersonDTO p = personConverter.toDTO(s.getPerson());
            sd.setPerson(p);

            if (s.getSongSetlists() != null && !s.getSongSetlists().isEmpty()) {
                List<SongDTO> sgList = new ArrayList<>();
                for (Song sg : s.getSongSetlists()
                        .stream()
                        .map(SongSetlist::getSong)
                        .collect(Collectors.toList())) {
                    sgList.add(songConverter.toDTO(sg));
                }
                sd.setSongs(sgList);
            }

            sdList.add(sd);
        }

        BaseDTO bd = new BaseDTO();
        bd.setSetlists(sdList);

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
        setlistControllerValidator.validateCreate(bdIn);

        // Convert
        Setlist s = setlistConverter.toModel(null, bdIn.getSetlist());
        Person p = null;
        if (bdIn.getSetlist().getPerson() != null) {
            p = personConverter.toModel(bdIn.getSetlist().getPerson().getId(), null);
        }

        List<Song> sList = new ArrayList<>();
        if (bdIn.getSetlist().getSongs() != null && !bdIn.getSetlist().getSongs().isEmpty()) {
            for (SongDTO sd : bdIn.getSetlist().getSongs()) {
                sList.add(songConverter.toModel(sd.getId(), sd));
            }
        }

        // Create
        Setlist sPersist = setlistService.create(s, p, sList);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSetlist(setlistConverter.toDTO(sPersist));

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
        setlistControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Setlist s = setlistConverter.toModel(id, bdIn.getSetlist());

        // Update
        Setlist sPersist = setlistService.update(s);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSetlist(setlistConverter.toDTO(sPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        setlistControllerValidator.validateDelete(id);

        //Delete
        setlistService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
