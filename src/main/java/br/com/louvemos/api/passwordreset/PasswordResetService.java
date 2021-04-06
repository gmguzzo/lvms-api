package br.com.louvemos.api.passwordreset;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.auth.PasswordUtils;
import br.com.louvemos.api.email.context.ForgotPasswordEmailContext;
import br.com.louvemos.api.email.service.EmailService;
import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.person.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PasswordResetService {

    @Autowired
    PersonService personService;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    SecureTokenRepository secureTokenRepository;

    @Value("${site.base.url.https}")
    private String baseURL;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonRepository personRepository;

    public void updatePassword(String password, String token) throws LvmsException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
            throw new LvmsException(LvmsCodesEnum.FORBIDDEN);
        }
        Person person = personRepository.loadById(secureToken.getPerson().getId());
        if (Objects.isNull(person)) {
            throw new LvmsException(LvmsCodesEnum.FORBIDDEN);
        }
        secureTokenService.removeToken(secureToken);
        person.setPassword(PasswordUtils.encode(password));
        person.setUpTimestamps();
        personRepository.update(person);
    }

    public void forgottenPassword(String email) throws LvmsException {
        List<Person> pList = personService.list(null, null, null, null, null, Arrays.asList(email), null, null, null);
        sendResetPasswordEmail(pList.get(0));
    }

    protected void sendResetPasswordEmail(Person person) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setPerson(person);
        secureTokenRepository.save(secureToken);
        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
        emailContext.init(person);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
