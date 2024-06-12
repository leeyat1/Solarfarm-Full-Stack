package learn.solarfarm.controllers;

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
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
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
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(List.of("Invalid password"), HttpStatus.FORBIDDEN);
        }
    }
}