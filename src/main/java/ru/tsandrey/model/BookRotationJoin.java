package ru.tsandrey.model;

import java.sql.Date;

public class BookRotationJoin implements Comparable<BookRotationJoin> {

    private int id;
    private int idPeople;
    private int idBook;
    private Date date;
    private String status;
    private String authorName;
    private String authorSurname;
    private String authorBirthday;
    private String booksName;
    private int year;
    private String peopleName;
    private String peopleSurname;
    private String phone;
    private String peopleBirthday;
    private String passport;


    public BookRotationJoin(int id, int idPeople, int idBook, Date date, String status, String authorName, String authorSurname, String authorBirthday, String booksName, int year, String peopleName, String peopleSurname, String phone, String peopleBirthday, String passport) {
        this.id = id;
        this.idPeople = idPeople;
        this.idBook = idBook;
        this.date = date;
        this.status = status;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.authorBirthday = authorBirthday;
        this.booksName = booksName;
        this.year = year;
        this.peopleName = peopleName;
        this.peopleSurname = peopleSurname;
        this.phone = phone;
        this.peopleBirthday = peopleBirthday;
        this.passport = passport;
    }

    public BookRotationJoin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPeople() {
        return idPeople;
    }

    public void setIdPeople(int idPeople) {
        this.idPeople = idPeople;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        if (status.equals("t")) {
            return "Взял";
        } else return "Вернул";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getAuthorBirthday() {
        return authorBirthday;
    }

    public void setAuthorBirthday(String authorBirthday) {
        this.authorBirthday = authorBirthday;
    }

    public String getBooksName() {
        return booksName;
    }

    public void setBooksName(String booksName) {
        this.booksName = booksName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleSurname() {
        return peopleSurname;
    }

    public void setPeopleSurname(String peopleSurname) {
        this.peopleSurname = peopleSurname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPeopleBirthday() {
        return peopleBirthday;
    }

    public void setPeopleBirthday(String peopleBirthday) {
        this.peopleBirthday = peopleBirthday;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }


    @Override
    public int compareTo(BookRotationJoin o) {
        return Integer.compare(this.id, o.id);
    }
}
