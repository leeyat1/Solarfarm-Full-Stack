package learn.solarfarm;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class SecretSigningKey {
    private Key key;

    public SecretSigningKey() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public Key getKey() {
        return key;
    }
}