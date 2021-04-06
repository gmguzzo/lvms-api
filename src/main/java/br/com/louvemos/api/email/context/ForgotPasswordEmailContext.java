package br.com.louvemos.api.email.context;

import org.springframework.web.util.UriComponentsBuilder;
import br.com.louvemos.api.person.Person;

public class ForgotPasswordEmailContext extends AbstractEmailContext {

    private String token;

    public <T> void init(T context) {
        Person person = (Person) context; // we pass the customer informati
        put("firstName", person.getFirstName());
        setTemplateLocation("/emails/forgot-password");
        setSubject("Forgotten Password");
        setFrom("no-reply@lvms.com");
        setTo(person.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/password/change").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
