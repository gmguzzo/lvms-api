package br.com.louvemos.api.auth;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Andersson
 */
public class PasswordUtils {

    private final static Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    public static String encode(CharSequence rawPassword) {
        String salt = BCrypt.gensalt(BCryptVersion.$2A.getVersion(), 10);
        return BCrypt.hashpw(rawPassword.toString(), salt);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() == 0) {
            return false;
        }

        if (!BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
            return false;
        }

        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }

    public enum BCryptVersion {
        $2A("$2a"),
        $2Y("$2y"),
        $2B("$2b");

        private final String version;

        BCryptVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return this.version;
        }
    }

}
