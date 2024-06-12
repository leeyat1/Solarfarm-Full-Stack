package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.UserRepository;
import learn.solarfarm.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void findByUsername() throws DataAccessException {
        User expected = new User(1, "test@test.com", "password");
        when(repository.findByUsername("test@test.com")).thenReturn(expected);

        User actual = service.findByUsername("test@test.com");

        assertEquals(expected, actual);
    }

    @Test
    void create() throws DataAccessException {
        User beforeCreate = new User(0, "test@test.com", "password");
        User afterCreate = new User(1, "test@test.com", "password");
        Result<User> expected = new Result<>();
        expected.setPayload(afterCreate);
        when(repository.create(beforeCreate)).thenReturn(afterCreate);

        Result<User> actual = service.create(beforeCreate);

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotCreateBlankUsername() throws DataAccessException {
        User user = new User(0, "", "password");
        Result<User> expected = new Result<>();
        expected.addErrorMessage("Username cannot be blank", ResultType.INVALID);

        Result<User> actual = service.create(user);

        assertEquals(expected, actual);
    }


    @Test
    void shouldNotCreateBlankPassword() throws DataAccessException {
        User user = new User(0, "test@test.com", "");
        Result<User> expected = new Result<>();
        expected.addErrorMessage("Password cannot be blank", ResultType.INVALID);

        Result<User> actual = service.create(user);

        assertEquals(expected, actual);
    }
}