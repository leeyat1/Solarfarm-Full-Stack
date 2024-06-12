package learn.solarfarm.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.Result;
import learn.solarfarm.domain.UserService;
import learn.solarfarm.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) throws DataAccessException {
        Result<User> result = service.create(user);

        if (result.isSuccess()) {
            String jwt = getJwtStringFromUser(result.getPayload());
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
            String jwt = getJwtStringFromUser(existingUser);

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(List.of("Invalid password"), HttpStatus.FORBIDDEN);
        }
    }

    private String getJwtStringFromUser(User user){
        String jwt = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("id", user.getId())
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
        return jwt;
    }
}