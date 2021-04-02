package br.com.louvemos.api.personshare;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.person.Person;
import br.com.louvemos.api.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PersonShareService {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonShareServiceValidator personShareServiceValidator;

    @Autowired
    private PersonShareRepository personShareRepository;

    public PersonShare load(Long id,
            PersonShareSubjectTypeEnum subType,
            Long subId,
            Long pTargetId) {
        if (id != null) {
            return personShareRepository.loadById(id);
        } else {
            return personShareRepository.loadBySubjectAndTarget(subType, subId, pTargetId);
        }
    }

    public PersonShare create(PersonShare personShare, Person owner, Person target) throws LvmsException {
        personShare.setUpTimestamps();

        Person ownerPersist = personService.load(owner.getId(), null);
        personShare.setOwnerPerson(ownerPersist);

        Person targetPersist = personService.load(target.getId(), null);
        personShare.setTargetPerson(targetPersist);

        personShareServiceValidator.validatePersist(personShare);

        return personShareRepository.save(personShare);
    }

    public void delete(Long pId) throws LvmsException {
        PersonShare p = personShareRepository.loadById(pId);
        personShareServiceValidator.validatePersonShareFound(p);

        personShareRepository.delete(p);
    }

}
