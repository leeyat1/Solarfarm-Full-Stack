package learn.solarfarm.data;

import learn.solarfarm.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    private UserJdbcTemplateRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
//        if (!hasSetup) {
//            hasSetup = true;
//            jdbcTemplate.update("call set_known_good_state();");
//        }
    }

    @Test
    void findByUsername() throws DataAccessException {
        User expected = new User(1, "test@test.com", "password");

        User actual = repository.findByUsername("test@test.com");

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByNonexistantUsername() throws DataAccessException {
        User actual = repository.findByUsername("fake@fake.com");

        assertNull(actual);
    }

    @Test
    void create() throws DataAccessException {
        User toCreate = new User(0, "test@test.com", "password");
        int countBeforeCreate = jdbcTemplate.queryForObject("select count(*) from user;", Integer.class);

        User afterCreate = repository.create(toCreate);

        assertNotEquals(0, afterCreate.getId());
        int countAfterCreate = jdbcTemplate.queryForObject("select count(*) from user;", Integer.class);
        assertEquals(countBeforeCreate + 1, countAfterCreate);
    }
}