/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.song;

import br.com.louvemos.api.artist.Artist;
import br.com.louvemos.api.album.Album;
import br.com.louvemos.api.album.AlbumConverter;
import br.com.louvemos.api.artist.ArtistConverter;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.SerializationUtils;
import br.com.louvemos.api.exception.LvmsException;
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
@RequestMapping(value = "/v2/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongControllerValidator songControllerValidator;

    @Autowired
    private SongConverter songConverter;

    @Autowired
    private AlbumConverter albumConverter;

    @Autowired
    private ArtistConverter artistConverter;

//    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public BaseDTO list(
//            @RequestParam(required = false, value = "ids") String ids,
//            @RequestParam(required = false, value = "symbols") String symbols,
//            @RequestParam(required = false, value = "qSymbol") String qSymbol,
//            @RequestParam(required = false, value = "firstResult") Integer firstResult,
//            @RequestParam(required = false, value = "maxResults") Integer maxResults,
//            @RequestParam(required = false, value = "sort") String sort
//    ) throws LvmsException {
//
//        songControllerValidator.validateList(ids, symbols, qSymbol, firstResult, maxResults, sort);
//
//        firstResult = ControllerUtils.adjustFirstResult(firstResult);
//        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
//        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);
//        List<Long> cIdList = ControllerUtils.parseCSVToLongList(ids);
//
//        List<String> symbolList = ControllerUtils.parseCSVToStringList(symbols);
//
//        List<Song> cList = songService.list(qSymbol, cIdList, symbolList, firstResult, maxResults, sortMap);
//
//        BaseDTO bd = new BaseDTO();
//        List<SongDTO> cdList = new ArrayList<>();
//        cList.forEach((c) -> {
//            cdList.add(songConverter.toDTO(c));
//        });
//
//        bd.setSongs(cdList);
//
//        return bd;
//    }
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO create(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        songControllerValidator.validateCreate(bdIn);

        // Convert
        Song s = songConverter.toModel(null, bdIn.getSong());
        Album a = albumConverter.toModel(bdIn.getSong().getAlbum().getId(), bdIn.getSong().getAlbum());

        Artist ar = null;
        if (bdIn.getSong().getAlbum().getArtist() != null) {
            ar = artistConverter.toModel(bdIn.getSong().getAlbum().getArtist().getId(), bdIn.getSong().getAlbum().getArtist());
        }

        // Create
        Song sPersist = songService.create(s, a, ar);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSong(songConverter.toDTO(sPersist));

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
        songControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Song s = songConverter.toModel(id, bdIn.getSong());

        // Update
        Song sPersist = songService.update(s);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSong(songConverter.toDTO(sPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        songControllerValidator.validateDelete(id);

        //Delete
        songService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
