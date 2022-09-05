package ru.tsandrey.model;


import javax.validation.constraints.NotEmpty;

public class Book {
    private int id;
    @NotEmpty(message = "Введите название")
    private String name;
//    @NotEmpty(message = "Выберите автора")
    private int idAuthor;
    private int year;

    public Book(int id, String name, int idAuthor, int year) {
        this.id = id;
        this.name = name;
        this.idAuthor = idAuthor;
        this.year = year;
    }

    public Book() {
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

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
