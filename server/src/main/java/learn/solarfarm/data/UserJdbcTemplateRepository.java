package learn.solarfarm.data;

import learn.solarfarm.models.SolarPanel;
import learn.solarfarm.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> mapper = (resultSet, rowIndex) -> {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    };

    @Override
    public User findByUsername(String username) throws DataAccessException {
        String sql = "select id, username, password from `user` where username = ?;";
        return jdbcTemplate.query(sql, mapper, username).stream().findFirst().orElse(null);
    }

    @Override
    public User create(User user) throws DataAccessException {
        String sql = "insert into user (username, password) values (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            return statement;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        user.setId(keyHolder.getKey().intValue());

        return user;
    }
}