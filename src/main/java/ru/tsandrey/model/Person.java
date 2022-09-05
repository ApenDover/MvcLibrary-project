package ru.tsandrey.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

public class Person {
    private int id;
    @NotEmpty(message = "Введите имя")
    private String name;
    @NotEmpty(message = "Введите фамилию")
    private String surname;
    @NotEmpty(message = "Введите телефон")
    @Pattern(regexp = "\\d*", message = "Только цифры")
    private String phone;
    @NotEmpty(message = "Введите паспорт")
    @Pattern(regexp = "\\d*", message = "Только цифры")
    private String passport;
    @NotEmpty(message = "Укажите дату рождения")
    private String birthday;
    private List<Book> books;

    public Person(int id, String name, String surname, String phone, String passport, String birthday, List<Book> books) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.passport = passport;
        this.birthday = birthday;
        this.books = books;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
