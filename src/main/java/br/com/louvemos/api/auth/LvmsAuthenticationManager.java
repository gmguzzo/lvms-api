/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.auth;

import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author gmguzzo
 */
@Component
public class LvmsAuthenticationManager implements AuthenticationManager {

    @Autowired
    private PersonService userService;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String username = a.getName();
        String password = a.getCredentials().toString();

        Person u = userService.load(null, username);

        if (u == null) {
            throw new BadCredentialsException("Usuário ou senha incorretos.");
        }

        if (!PasswordUtils.matches(password, u.getPassword())) {
            throw new BadCredentialsException("Usuário ou senha incorretos.");
        }

        return new UsernamePasswordAuthenticationToken(username, password);
    }

}
