package sec.project.config;

import org.springframework.security.crypto.password.PasswordEncoder;
/**
 *
 * @author joonas
 */
public class NoEncodingPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();

    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return cs.toString().equals(string);

    }
}
