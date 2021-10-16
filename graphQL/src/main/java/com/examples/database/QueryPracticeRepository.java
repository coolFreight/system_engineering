package com.examples.database;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface QueryPracticeRepository {

    @SqlQuery("select * from test_user")
    @RegisterRowMapper(TestUserMapper.class)
    List<TestUser> getTestUsers();

    @SqlUpdate("insert into test_user  (id, data, type,  typeDate) values (:id, :data::jsonb, :type, :typeDate)")
    void addUser(@BindBean TestUser user);

}
