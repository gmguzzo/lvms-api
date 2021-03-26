/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.songsetlist;

import br.com.louvemos.api.base.BaseController;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.SerializationUtils;
import br.com.louvemos.api.setlist.SetlistConverter;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.song.*;
import br.com.louvemos.api.setlist.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author gmguzzo
 */
@Controller
@RequestMapping(value = "/v2/songsetlists")
public class SongSetlistController extends BaseController {

    @Autowired
    private SongSetlistService songSetlistService;

    @Autowired
    private SongSetlistControllerValidator songSetlistControllerValidator;

    @Autowired
    private SongConverter songConverter;

    @Autowired
    private SetlistConverter setlistConverter;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO create(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        songSetlistControllerValidator.validateCreate(bdIn);

        // Convert
        Song s = songConverter.toModel(bdIn.getSongSetlist().getSong().getId(), null);
        Setlist c = setlistConverter.toModel(bdIn.getSongSetlist().getSetlist().getId(), null);

        // Create
        SongSetlist sPersist = songSetlistService.create(new SongSetlist(), s, c);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        SongDTO song = songConverter.toDTO(sPersist.getSong());
        SetlistDTO setlist = setlistConverter.toDTO(sPersist.getSetlist());
        SongSetlistDTO songSetlist = new SongSetlistDTO();
        songSetlist.setSong(song);
        songSetlist.setSetlist(setlist);

        bdOut.setSongSetlist(songSetlist);

        return bdOut;
    }

    @RequestMapping(value = "{songId}/{setlistId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "songId") Long songId,
            @PathVariable(value = "setlistId") Long setlistId
    ) throws LvmsException {
        songSetlistControllerValidator.validateDelete(songId);

        //Delete
        songSetlistService.delete(null, songId, setlistId);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
