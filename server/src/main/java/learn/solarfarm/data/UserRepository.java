package learn.solarfarm.data;

import learn.solarfarm.models.User;

public interface UserRepository {
    User findByUsername(String username) throws DataAccessException;
    User create(User user) throws DataAccessException;
}