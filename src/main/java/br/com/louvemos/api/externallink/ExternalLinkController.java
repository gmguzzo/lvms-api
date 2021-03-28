/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import br.com.louvemos.api.base.BaseController;
import br.com.louvemos.api.song.*;
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

@Controller
@RequestMapping(value = "/v2/externallinks")
public class ExternalLinkController extends BaseController {

    @Autowired
    private ExternalLinkControllerValidator externalLinkControllerValidator;

    @Autowired
    private ExternalLinkService externalLinkService;

    @Autowired
    private ExternalLinkConverter externalLinkConverter;

    @Autowired
    private SongConverter songConverter;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO create(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        externalLinkControllerValidator.validateCreate(bdIn);

        // Convert
        ExternalLink el = externalLinkConverter.toModel(null, bdIn.getExternalLink());
        Song s = songConverter.toModel(bdIn.getExternalLink().getSong().getId(), null);

        // Create
        ExternalLink elPersist = externalLinkService.create(el, s);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        ExternalLinkDTO ej = externalLinkConverter.toDTO(elPersist);
        bdOut.setExternalLink(ej);
        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        externalLinkControllerValidator.validateDelete(id);

        externalLinkService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;
    }

}
