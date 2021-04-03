/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.person;

import br.com.louvemos.api.auth.MyUserDetails;
import br.com.louvemos.api.base.*;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.roleperson.RolePerson;
import br.com.louvemos.api.role.*;
import br.com.louvemos.api.personshare.PersonShare;
import br.com.louvemos.api.personshare.PersonShareConverter;
import br.com.louvemos.api.personshare.PersonShareDTO;
import br.com.louvemos.api.personshare.PersonShareService;
import br.com.louvemos.api.role.Role;
import br.com.louvemos.api.role.RoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
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

    @Autowired
    private PersonShareConverter personShareConverter;

    @Autowired
    private PersonShareService personShareService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO list(
            @RequestParam(required = false, value = "ids") String ids,
            @RequestParam(required = false, value = "q") String q,
            @RequestParam(required = false, value = "usernames") String usernames,
            @RequestParam(required = false, value = "firstNames") String firstNames,
            @RequestParam(required = false, value = "lastNames") String lastNames,
            @RequestParam(required = false, value = "emails") String emails,
            @RequestParam(required = false, value = "firstResult") Integer firstResult,
            @RequestParam(required = false, value = "maxResults") Integer maxResults,
            @RequestParam(required = false, value = "sort") String sort
    ) throws LvmsException {

        List<Long> idList = ControllerUtils.parseCSVToLongList(ids);
        List<String> firstNameList = ControllerUtils.parseCSVToStringList(firstNames);
        List<String> lastNameList = ControllerUtils.parseCSVToStringList(lastNames);
        List<String> emailList = ControllerUtils.parseCSVToStringList(emails);
        List<String> usernameList = ControllerUtils.parseCSVToStringList(usernames);

        firstResult = ControllerUtils.adjustFirstResult(firstResult);
        maxResults = ControllerUtils.adjustMaxResults(maxResults, 20, 40);
        LinkedHashMap<String, SortDirectionEnum> sortMap = ControllerUtils.parseSortParam(sort);

        List<Person> list = personService.list(q, idList, usernameList, firstNameList, lastNameList, emailList, firstResult, maxResults, sortMap);

        BaseDTO bd = new BaseDTO();
        embedPersonOnBaseDTO(bd, list);

        return bd;
    }

    @RequestMapping(value = "/self", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO self() throws LvmsException {
        MyUserDetails authDetails = null;
        try {
            authDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException | NullPointerException e) {
            throw new LvmsException(LvmsCodesEnum.FORBIDDEN);
        }
        Person pPersist = personService.load(null, authDetails.getUsername());

        BaseDTO bd = new BaseDTO();
        embedSelfOnBaseDTO(bd, Arrays.asList(pPersist));

        return bd;
    }

    @RequestMapping(value = "share", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO share(
            @RequestBody String body
    ) throws LvmsException {
        BaseDTO bdIn = SerializationUtils.fromJson(body, BaseDTO.class);

        personControllerValidator.validateShare(bdIn);

        Person pOwner = personConverter.toModel(bdIn.getPerson().getId(), null);
        Person pTarget = personConverter.toModel(bdIn.getPerson().getPersonShare().getTargetPerson().getId(), null);
        PersonShare personShare = personShareConverter.toModel(null, bdIn.getPerson().getPersonShare());

        PersonShare psPersist = personShareService.create(personShare, pOwner, pTarget);

        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso.");
        return bd;
    }

    @RequestMapping(value = "unshare/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public BaseDTO unshare(
            @PathVariable(value = "id") Long id
    ) throws LvmsException {

        personControllerValidator.validateDelete(id);

        personShareService.delete(id);

        BaseDTO bd = new BaseDTO();
        bd.setMessage("Operação realizada com sucesso.");
        return bd;
    }

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

    private void embedPersonOnBaseDTO(BaseDTO bd, List<Person> list) {
        List<PersonDTO> pList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Person p : list) {
                pList.add(personConverter.toDTO(p));
            }
        }
        bd.setPersons(pList);
    }

    private void embedSelfOnBaseDTO(BaseDTO bd, List<Person> list) {
        List<PersonDTO> pList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Person p : list) {
                List<PersonShareDTO> psdList = new ArrayList<>();
                PersonDTO pd = personConverter.toDTO(p);
                if (p.getSharedResources() != null && !p.getSharedResources().isEmpty()) {
                    for (PersonShare sharedResource : p.getSharedResources()) {
                        PersonShareDTO psd = personShareConverter.toDTO(sharedResource);
                        psd.setTargetPerson(personConverter.toDTO(sharedResource.getTargetPerson()));
                        psdList.add(psd);
                    }
                    pd.setSharedResources(psdList);
                }

                if (p.getRolePersons() != null && !p.getRolePersons().isEmpty()) {
                    List<RoleDTO> roles = new ArrayList<>();
                    for (Role r : p.getRolePersons()
                            .stream()
                            .map(RolePerson::getRole)
                            .collect(Collectors.toList())) {
                        roles.add(roleConverter.toDTO(r));

                    }
                    pd.setRoles(roles);
                }
                pList.add(pd);
            }
        }
        bd.setPerson(pList.get(0));
    }

}
