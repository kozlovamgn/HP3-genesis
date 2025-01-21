package com.example.genesis.storage;

import com.example.genesis.model.UserEntity;
import org.intellij.lang.annotations.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {
    @Language("SQL")
    private static final String INSERT_USER_SQL =
            "insert into users " +
            "   (id, name, surname, person_id, uuid) " +
            "values " +
            "   (?, ?, ?, ?, ?)";

    @Language("SQL")
    private static final String UPDATE_USER_SQL =
            "update users set" +
            "   name = ?, " +
            "   surname = ? " +
            "where id = ?";

    @Language("SQL")
    private static final String SELECT_BY_ID =
            "select * from users " +
            "where id = ?";

    @Language("SQL")
    private static final String SELECT_ALL =
            "select * from users";



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(UserEntity user) {
        jdbcTemplate.update(
                INSERT_USER_SQL,
                user.id(),
                user.name(),
                user.surname(),
                user.personId(),
                user.uuid().toString());
    }

    public UserEntity findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_BY_ID,
                    new Object[]{
                            id
                    },
                    new int[]{
                            Types.INTEGER
                    },
                    userEntityRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<UserEntity> findAll() {
        return jdbcTemplate.query(
                SELECT_ALL,
                userEntityRowMapper()
        );
    }

    public int updateUser(Long id, String name, String surname) {
        return jdbcTemplate.update(
                UPDATE_USER_SQL,
                name,
                surname,
                id
        );
    }

    private static RowMapper<UserEntity> userEntityRowMapper() {
        return (rs, rowNum) -> new UserEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("person_id"),
                UUID.fromString(rs.getString("uuid"))
        );
    }
}
