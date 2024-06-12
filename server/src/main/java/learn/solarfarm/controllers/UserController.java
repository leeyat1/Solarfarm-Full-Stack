package learn.solarfarm.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import learn.solarfarm.SecretSigningKey;
import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.Result;
import learn.solarfarm.domain.UserService;
import learn.solarfarm.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService service;

    private final SecretSigningKey secretSigningKey;

    public UserController(UserService service, SecretSigningKey secretSigningKey) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) throws DataAccessException {
        Result<User> result = service.create(user);

        if (result.isSuccess()) {
            HashMap<String, String> jwt = getJwtFromUser(result.getPayload());
            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws DataAccessException {
        User existingUser = service.findByUsername(user.getUsername());

        if (existingUser == null) {
            return new ResponseEntity<>(List.of("That username does not exist"), HttpStatus.NOT_FOUND);
        } else if (existingUser.getPassword().equals(user.getPassword())) {
            HashMap<String, String> jwt = getJwtFromUser(existingUser);
            return new ResponseEntity<>(jwt , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(List.of("Invalid password"), HttpStatus.FORBIDDEN);
        }
    }

    private HashMap<String, String> getJwtFromUser(User user) {
        String jwt =  Jwts.builder()
                .claim("username", user.getUsername())
                .claim("id", user.getId())
                .signWith(secretSigningKey.getKey())
                .compact();
        HashMap<String, String> output = new HashMap<>();
        output.put("jwt", jwt);
        return output;
    }
}