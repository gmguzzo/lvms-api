/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.artist;

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
@RequestMapping(value = "/v2/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistControllerValidator artistControllerValidator;

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
//        artistControllerValidator.validateList(ids, symbols, qSymbol, firstResult, maxResults, sort);
//
//        firstResult = ControllerUtils.adjustFirstResult(firstResult);
//        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
//        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);
//        List<Long> cIdList = ControllerUtils.parseCSVToLongList(ids);
//
//        List<String> symbolList = ControllerUtils.parseCSVToStringList(symbols);
//
//        List<Artist> cList = artistService.list(qSymbol, cIdList, symbolList, firstResult, maxResults, sortMap);
//
//        BaseDTO bd = new BaseDTO();
//        List<ArtistDTO> cdList = new ArrayList<>();
//        cList.forEach((c) -> {
//            cdList.add(artistConverter.toDTO(c));
//        });
//
//        bd.setArtists(cdList);
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
        artistControllerValidator.validateCreate(bdIn);

        // Convert
        Artist a = artistConverter.toModel(null, bdIn.getArtist());

        // Create
        Artist aPersist = artistService.create(a);

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
