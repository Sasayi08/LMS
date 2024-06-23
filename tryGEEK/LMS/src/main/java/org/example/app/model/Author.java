package org.example.app.model;

import java.util.UUID;

public class Author {
    private final String firstname, surname;

    public Author(String firstName, String surname) {
        this.firstname = firstName;
        this.surname = surname;

    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }
}
