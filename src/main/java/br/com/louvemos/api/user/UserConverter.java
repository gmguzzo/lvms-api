package br.com.louvemos.api.user;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toModel(Long id, UserDTO ud) {
        if (ud == null) {
            if (id == null) {
                return null;
            }
            return new User(id);
        }

        User u = new User();

        u.setId(id);
        u.setUsername(ud.getUsername());
        u.setPassword(ud.getPassword());

        return u;
    }

    public UserDTO toDTO(User u) {
        if (u == null) {
            return null;
        }

        UserDTO ud = new UserDTO();
        ud.setId(u.getId());
        ud.setUsername(u.getUsername());
        ud.setPassword(u.getPassword());

        return ud;
    }

}
