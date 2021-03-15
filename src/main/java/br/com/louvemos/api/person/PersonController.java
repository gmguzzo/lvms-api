/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.person;

import br.com.louvemos.api.base.BaseController;
import br.com.louvemos.api.base.BaseDTO;
import br.com.louvemos.api.base.SerializationUtils;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.role.RoleConverter;
import br.com.louvemos.api.role.Role;
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
@RequestMapping(value = "/v2/persons")
public class PersonController extends BaseController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonControllerValidator personControllerValidator;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private RoleConverter roleConverter;

    @RequestMapping(value = "{id}/assignrole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO assignRole(
            @PathVariable(value = "id") Long id,
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        // Validate
        personControllerValidator.validateAssignRole(id, bdIn);

        // Convert
        Person p = personConverter.toModel(id, null);
        Role r = roleConverter.toModel(null, bdIn.getPerson().getRole());

        // Assign role
        personService.assignRole(p, r);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
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
        personControllerValidator.validateCreate(bdIn);

        // Convert
        Person p = personConverter.toModel(null, bdIn.getPerson());

        // Create
        Person pPersist = personService.create(p);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setPerson(personConverter.toDTO(pPersist));

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
        personControllerValidator.validateUpdate(id, bdIn);

        // Convert
        Person p = personConverter.toModel(id, bdIn.getPerson());

        // Update
        Person pPersist = personService.update(p);

        // Embed
        BaseDTO bdOut = new BaseDTO();
        bdOut.setPerson(personConverter.toDTO(pPersist));

        return bdOut;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO delete(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {
        //Validate
        personControllerValidator.validateDelete(id);

        //Delete
        personService.delete(id);

        //Embed
        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso");
        return bd;

    }

}
