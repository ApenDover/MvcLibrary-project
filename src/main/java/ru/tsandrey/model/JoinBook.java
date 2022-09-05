package ru.tsandrey.model;

public class JoinBook {
    private int id;
    private int idAuthor;
    private String nameBook;
    private int yearBook;
    private String nameAuthor;
    private String surnameAuthor;

    public JoinBook(int id, int idAuthor, String nameBook, int yearBook, String nameAuthor, String authorSurname) {
        this.id = id;
        this.idAuthor = idAuthor;
        this.nameBook = nameBook;
        this.yearBook = yearBook;
        this.nameAuthor = nameAuthor;
        this.surnameAuthor = authorSurname;
    }

    public JoinBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getYearBook() {
        return yearBook;
    }

    public void setYearBook(int yearBook) {
        this.yearBook = yearBook;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getSurnameAuthor() {
        return surnameAuthor;
    }

    public void setSurnameAuthor(String surnameAuthor) {
        this.surnameAuthor = surnameAuthor;
    }
}
