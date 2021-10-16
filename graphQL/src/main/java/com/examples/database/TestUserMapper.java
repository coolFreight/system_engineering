package com.examples.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestUserMapper implements RowMapper<TestUser> {

    static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TestUser map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new TestUser(rs.getString("id"), rs.getString("data"), rs.getString("type"), rs.getTimestamp("typeDate").toInstant());
    }
}
