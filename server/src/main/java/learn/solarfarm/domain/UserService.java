package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.UserRepository;
import learn.solarfarm.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByUsername(String username) throws DataAccessException {
        return repository.findByUsername(username);
    }

    public Result<User> create(User user) throws DataAccessException {
        Result<User> result = new Result<>();

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addErrorMessage("Username cannot be blank", ResultType.INVALID);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addErrorMessage("Password cannot be blank", ResultType.INVALID);
        }

        if (result.isSuccess()) {
            User createdUser = repository.create(user);
            result.setPayload(createdUser);
        }

        return result;
    }
}