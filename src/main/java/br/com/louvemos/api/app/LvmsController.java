/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.app;

import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.user.Person;
import br.com.louvemos.api.user.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author gmguzzo
 */
@Controller
public class LvmsController {

    @Autowired
    PersonService userService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void ping() throws LvmsException {

        Person p = new Person();
        p.setUsername("joel");
        p.setPassword("monstro");
        userService.create(p);

        Person p2 = new Person();
        p2.setUsername("gabriel");
        p2.setPassword("palmeiras");
        userService.create(p2);

    }
}
