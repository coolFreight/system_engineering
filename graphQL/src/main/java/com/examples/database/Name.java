package com.examples.database;

import java.time.format.DateTimeFormatter;

public class Name {

    private String firstName;
    private String middleName;
    private String lastName;
    private String date;

    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    public Name(String firstName, String middleName, String lastName, String date) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                '}';
    }
}
