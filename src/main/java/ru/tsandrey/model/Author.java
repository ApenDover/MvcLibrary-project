package ru.tsandrey.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class Author {
    private int id;
    @NotEmpty(message = "Введите имя")
    private String name;
    @NotEmpty(message = "Введите фамилию")
    private String surname;
    @NotEmpty(message = "Укажите дату рождения")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Формат должен быть YYYY-MM-DD")
    private String birthday;
    private List<Book> bookList;

    public Author(int id, String name, String surname, String birthday, List<Book> bookList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.bookList = bookList;
    }

    public Author() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
