/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.album;

import br.com.louvemos.api.artist.Artist;
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
@RequestMapping(value = "/v2/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumControllerValidator albumControllerValidator;

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
//        albumControllerValidator.validateList(ids, symbols, qSymbol, firstResult, maxResults, sort);
//
//        firstResult = ControllerUtils.adjustFirstResult(firstResult);
//        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
//        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);
//        List<Long> cIdList = ControllerUtils.parseCSVToLongList(ids);
//
//        List<String> symbolList = ControllerUtils.parseCSVToStringList(symbols);
//
//        List<Album> cList = albumService.list(qSymbol, cIdList, symbolList, firstResult, maxResults, sortMap);
//
//        BaseDTO bd = new BaseDTO();
//        List<AlbumDTO> cdList = new ArrayList<>();
//        cList.forEach((c) -> {
//            cdList.add(albumConverter.toDTO(c));
//        });
//
//        bd.setAlbums(cdList);
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
        albumControllerValidator.validateCreate(bdIn);

        // Convert
        Album a = albumConverter.toModel(null, bdIn.getAlbum());
        Artist ar = artistConverter.toModel(null, bdIn.getAlbum().getArtist());

        // Create
        Album sPersist = albumService.create(a, ar);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setAlbum(albumConverter.toDTO(sPersist));

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
        albumControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Album s = albumConverter.toModel(id, bdIn.getAlbum());

        // Update
        Album sPersist = albumService.update(s);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setAlbum(albumConverter.toDTO(sPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        albumControllerValidator.validateDelete(id);

        //Delete
        albumService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
