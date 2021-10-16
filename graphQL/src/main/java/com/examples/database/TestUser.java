package com.examples.database;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.Instant;
import java.util.Objects;

public class TestUser {

//    id varchar(50) NOT NULL PRIMARY KEY,
//    data jsonb,
//    type varchar(10)

    private String id;
    private String data;
    private String type;
    private Instant typeDate;

    @JsonCreator
    public TestUser(String id, String data, String type, Instant typeDate) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.typeDate = typeDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                ", type='" + type + '\'' +
                ", typeDate=" + typeDate+
                '}';
    }

    public Instant getTypeDate() {
        return typeDate;
    }

    public void setTypeDate(Instant typeDate) {
        this.typeDate = typeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestUser)) return false;
        TestUser testUser = (TestUser) o;
        return Objects.equals(id, testUser.id) && Objects.equals(typeDate, testUser.typeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeDate);
    }
}
