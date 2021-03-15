package br.com.louvemos.api.person;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.auth.PasswordUtils;
import br.com.louvemos.api.role.Role;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.roleperson.RolePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PersonService {

    @Autowired
    private PersonServiceValidator personServiceValidator;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolePersonService rolePersonService;

    public void assignRole(Person p, Role r) throws LvmsException {
        rolePersonService.create(r, p);
    }

    public Person update(Person p) throws LvmsException {
        Person pPersist = load(p.getId(), null);
        personServiceValidator.validatePersonFound(pPersist);

        pPersist.setUsername(p.getUsername());
        pPersist.setPassword(PasswordUtils.encode(p.getPassword()));

        pPersist.setUpTimestamps();
        personServiceValidator.validatePersist(pPersist);

        return personRepository.update(pPersist);

    }

    public Person load(Long id, String username) {
        if (id != null) {
            return personRepository.loadById(id);
        } else {
            return personRepository.loadByUsername(username);
        }
    }

    public Person create(Person person) throws LvmsException {
        if (person.getPassword() != null) {
            person.setPassword(PasswordUtils.encode(person.getPassword()));
        }
        person.setUpTimestamps();

        personServiceValidator.validatePersist(person);

        return personRepository.save(person);
    }

    public void delete(Long pId) throws LvmsException {
        Person p = personRepository.loadById(pId);
        personServiceValidator.validatePersonFound(p);

        personRepository.delete(p);
    }

}
