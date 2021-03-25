/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.category;

import br.com.louvemos.api.base.BaseController;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.ControllerUtils;
import br.com.louvemos.api.base.SerializationUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author gmguzzo
 */
@Controller
@RequestMapping(value = "/v2/categories")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryControllerValidator categoryControllerValidator;

    @Autowired
    private CategoryConverter categoryConverter;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO list(
            @RequestParam(required = false, value = "ids") String ids,
            @RequestParam(required = false, value = "q") String q,
            @RequestParam(required = false, value = "firstResult") Integer firstResult,
            @RequestParam(required = false, value = "maxResults") Integer maxResults,
            @RequestParam(required = false, value = "sort") String sort
    ) throws LvmsException {

        firstResult = ControllerUtils.adjustFirstResult(firstResult);
        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);
        List<Long> idList = ControllerUtils.parseCSVToLongList(ids);

        List<Category> cList = categoryService.list(idList, q, firstResult, maxResults, sortMap);

        List<CategoryDTO> cdList = new ArrayList<>();
        for (Category c : cList) {
            cdList.add(categoryConverter.toDTO(c));
        }

        BaseDTO bjOut = new BaseDTO();
        bjOut.setCategories(cdList);
        return bjOut;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO create(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        categoryControllerValidator.validateCreate(bdIn);

        // Convert
        Category c = categoryConverter.toModel(null, bdIn.getCategory());

        // Create
        Category cPersist = categoryService.create(c);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setCategory(categoryConverter.toDTO(cPersist));

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
        categoryControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Category c = categoryConverter.toModel(id, bdIn.getCategory());

        // Update
        Category cPersist = categoryService.update(c);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setCategory(categoryConverter.toDTO(cPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        categoryControllerValidator.validateDelete(id);

        //Delete
        categoryService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
