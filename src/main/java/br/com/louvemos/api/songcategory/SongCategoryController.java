/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.songcategory;

import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.SerializationUtils;
import br.com.louvemos.api.category.CategoryConverter;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.song.*;
import br.com.louvemos.api.category.*;
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
@RequestMapping(value = "/v2/songcategories")
public class SongCategoryController {

    @Autowired
    private SongCategoryService songCategoryService;

    @Autowired
    private SongCategoryControllerValidator songCategoryControllerValidator;

    @Autowired
    private SongCategoryConverter songCategoryConverter;

    @Autowired
    private SongConverter songConverter;

    @Autowired
    private CategoryConverter categoryConverter;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO create(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        songCategoryControllerValidator.validateCreate(bdIn);

        // Convert
        SongCategory sc = songCategoryConverter.toModel(null, bdIn.getSongCategory());
        Song s = songConverter.toModel(bdIn.getSongCategory().getSong().getId(), null);
        Category c = categoryConverter.toModel(bdIn.getSongCategory().getCategory().getId(), null);

        // Create
        SongCategory sPersist = songCategoryService.create(sc, s, c);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSongCategory(songCategoryConverter.toDTO(sPersist));

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
        songCategoryControllerValidator.validateUpdate(id, bdIn);

        // Convert
        SongCategory s = songCategoryConverter.toModel(id, bdIn.getSongCategory());

        // Update
        SongCategory sPersist = songCategoryService.update(s);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setSongCategory(songCategoryConverter.toDTO(sPersist));

        return bdOut;
    }

    @RequestMapping(value = "{songId}/{categoryId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "songId") Long songId,
            @PathVariable(value = "categoryId") Long categoryId
    ) throws LvmsException {
        songCategoryControllerValidator.validateDelete(songId);

        //Delete
        songCategoryService.delete(null, songId, categoryId);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
