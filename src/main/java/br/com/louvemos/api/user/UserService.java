package br.com.louvemos.api.user;

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
public class UserService {

    @Autowired
    private UserServiceValidator userServiceValidator;

    @Autowired
    private UserRepository userRepository;

    public User update(User u) throws LvmsException {
        User uPersist = load(u.getId(), null);
        userServiceValidator.validateUserFound(uPersist);

        uPersist.setUsername(u.getUsername());
        uPersist.setPassword(u.getPassword());

        uPersist.setUpTimestamps();
        userServiceValidator.validatePersist(uPersist);

        return userRepository.update(uPersist);

    }

    public User load(Long id, String username) {
        if (id != null) {
            return userRepository.loadById(id);
        } else {
            return userRepository.loadByUsername(username);
        }
    }

    public User create(User user) throws LvmsException {
        user.setUpTimestamps();

        userServiceValidator.validatePersist(user);

        return userRepository.save(user);
    }

    public void delete(Long cId) throws LvmsException {
        User c = userRepository.loadById(cId);
        userServiceValidator.validateUserFound(c);

        userRepository.delete(c);
    }

}
