package br.com.louvemos.api.roleperson;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.role.*;
import br.com.louvemos.api.person.*;
import br.com.louvemos.api.exception.LvmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RolePersonService {

    @Autowired
    private RolePersonServiceValidator rolePersonServiceValidator;

    @Autowired
    private RolePersonRepository rolePersonRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleServiceValidator roleServiceValidator;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonServiceValidator personServiceValidator;

    public RolePerson create(Role role, Person p) throws LvmsException {
        RolePerson rp = new RolePerson();

        Role rPersist = roleService.load(role.getId(), role.getName());
        roleServiceValidator.validateRoleFound(rPersist);
        rp.setRole(rPersist);

        Person pPersist = personService.load(p.getId(), p.getUsername());
        personServiceValidator.validatePersonFound(pPersist);
        rp.setPerson(pPersist);

        rp.setUpTimestamps();

        rolePersonServiceValidator.validatePersist(rp);

        return rolePersonRepository.save(rp);
    }

    public void delete(Long rId) throws LvmsException {
        RolePerson p = rolePersonRepository.loadById(rId);
        rolePersonServiceValidator.validateNull(p);

        rolePersonRepository.delete(p);
    }

}
