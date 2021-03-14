package br.com.louvemos.api.role;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RoleService {

    @Autowired
    private RoleServiceValidator roleServiceValidator;

    @Autowired
    private RoleRepository roleRepository;

    public Role update(Role r) throws LvmsException {
        Role rPersist = load(r.getId(), null);
        roleServiceValidator.validateRoleFound(rPersist);

        rPersist.setName(r.getName());

        rPersist.setUpTimestamps();
        roleServiceValidator.validatePersist(rPersist);

        return roleRepository.update(rPersist);

    }

    public Role load(Long id, String name) {
        if (id != null) {
            return roleRepository.loadById(id);
        } else {
            return roleRepository.loadByName(name);
        }
    }

    public Role create(Role role) throws LvmsException {
        role.setUpTimestamps();

        roleServiceValidator.validatePersist(role);

        return roleRepository.save(role);
    }

    public void delete(Long rId) throws LvmsException {
        Role p = roleRepository.loadById(rId);
        roleServiceValidator.validateRoleFound(p);

        roleRepository.delete(p);
    }

}
