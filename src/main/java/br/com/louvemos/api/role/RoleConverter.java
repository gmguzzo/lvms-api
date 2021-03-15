package br.com.louvemos.api.role;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    public Role toModel(Long id, RoleDTO rd) {
        if (rd == null) {
            if (id == null) {
                return null;
            }
            return new Role(id);
        }

        Role r = new Role();

        r.setId(id);
        r.setName(rd.getName());

        return r;
    }

    public RoleDTO toDTO(Role r) {
        if (r == null) {
            return null;
        }

        RoleDTO rd = new RoleDTO();
        rd.setId(r.getId());
        rd.setName(r.getName());

        return rd;
    }

}
