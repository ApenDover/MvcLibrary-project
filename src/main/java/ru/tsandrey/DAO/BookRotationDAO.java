package ru.tsandrey.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tsandrey.model.BookRotationJoin;
import ru.tsandrey.model.Person;

import java.util.Collections;
import java.util.List;

@Component
public class BookRotationDAO {
    JdbcTemplate jdbcTemplate;
    private int Integer;

    @Autowired
    public BookRotationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookRotationJoin> allRorations() {
        return jdbcTemplate.query("SELECT bookrotation.id as id, idpeople, idbook, date, status, author.name as authorName, author.surname as authorSurname, author.birthday as authorBirthday, books.name as booksName, year, people.name as peopleName, people.surname as peopleSurname, phone, people.birthday as peopleBirthday, passport FROM bookrotation INNER JOIN books ON bookrotation.idbook = books.id INNER JOIN people ON bookrotation.idpeople = people.id INNER JOIN author ON books.idauthor = author.id", new BeanPropertyRowMapper<>(BookRotationJoin.class));
    }

    public List<BookRotationJoin> bookRotationByIdPerson(int id) {
        List<BookRotationJoin> bookRotationJoinList;
        bookRotationJoinList = jdbcTemplate.query("SELECT bookrotation.id as id, idpeople, idbook, date, status, author.name as authorName, author.surname as authorSurname, author.birthday as authorBirthday, books.name as booksName, year, people.name as peopleName, people.surname as peopleSurname, phone, people.birthday as peopleBirthday, passport FROM bookrotation INNER JOIN books ON bookrotation.idbook = books.id INNER JOIN people ON bookrotation.idpeople = people.id INNER JOIN author ON books.idauthor = author.id WHERE people.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(BookRotationJoin.class));
        Collections.reverse(bookRotationJoinList);
        return bookRotationJoinList;
    }

    public List<BookRotationJoin> bookRotationByIdBook(int id) {
        return jdbcTemplate.query("SELECT bookrotation.id as id, idpeople, idbook, date, status, author.name as authorName, author.surname as authorSurname, author.birthday as authorBirthday, books.name as booksName, year, people.name as peopleName, people.surname as peopleSurname, phone, people.birthday as peopleBirthday, passport FROM bookrotation INNER JOIN books ON bookrotation.idbook = books.id INNER JOIN people ON bookrotation.idpeople = people.id INNER JOIN author ON books.idauthor = author.id WHERE books.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(BookRotationJoin.class));
    }

    public List<BookRotationJoin> bookRotationNowByIdPerson(int id) {
        return jdbcTemplate.query("SELECT bookrotation.id as id, bookrotation.idpeople as idPeople, bookrotation.idbook as idBook, bookrotation.date as date, bookrotation.status as status, author.name as authorName, author.surname as authorSurname, author.birthday as authorBirthday, books.name as booksName, year, people.name as peopleName, people.surname as peopleSurname, phone, people.birthday as peopleBirthday, passport FROM bookrotation INNER JOIN books ON bookrotation.idbook = books.id INNER JOIN people ON bookrotation.idpeople = people.id INNER JOIN author ON books.idauthor = author.id INNER JOIN (SELECT idbook, idpeople FROM (SELECT count(*) as parametr, idbook, idpeople FROM bookrotation WHERE idpeople = ? GROUP BY idbook, idpeople) as how WHERE parametr % 2 = 1) as rezult ON rezult.idpeople = bookrotation.idpeople WHERE people.id = ? and rezult.idbook = bookrotation.idbook", new Object[]{id, id}, new BeanPropertyRowMapper<>(BookRotationJoin.class));
    }

    public List<BookRotationJoin> bookRotationNowByIdBook(int id) {
        return jdbcTemplate.query("SELECT bookrotation.id as id, bookrotation.idpeople as idPeople, bookrotation.idbook as idBook, bookrotation.date as date, bookrotation.status as status, author.name as authorName, author.surname as authorSurname, author.birthday as authorBirthday, books.name as booksName, year, people.name as peopleName, people.surname as peopleSurname, phone, people.birthday as peopleBirthday, passport FROM bookrotation INNER JOIN books ON bookrotation.idbook = books.id INNER JOIN people ON bookrotation.idpeople = people.id INNER JOIN author ON books.idauthor = author.id INNER JOIN (SELECT idbook, idpeople FROM (SELECT count(*) as parametr, idbook, idpeople FROM bookrotation WHERE idbook = ? GROUP BY idbook, idpeople) as how WHERE parametr % 2 = 1) as rezult ON rezult.idpeople = bookrotation.idpeople WHERE books.id = ? and rezult.idbook = bookrotation.idbook", new Object[]{id, id}, new BeanPropertyRowMapper<>(BookRotationJoin.class));
    }

    public void backBook(int idperson, int idbook) {
        jdbcTemplate.update("INSERT INTO bookrotation(idpeople, idbook, status) VALUES (?, ?, ?)", idperson, idbook, false);
    }

    public void giveBook(int idbook, int idperson) {
        jdbcTemplate.update("INSERT INTO bookrotation(idpeople, idbook, status) VALUES (?, ?, ?)", idperson, idbook, true);
    }

    public int howManyTake(int id) {
        int one;
        one = jdbcTemplate.queryForObject("SELECT COUNT(*)/2 as takeCount FROM bookrotation WHERE idbook = ?", new Object[]{id}, Integer.class);
        return one;
    }

    public Person whoWasLast(int id) {
        return jdbcTemplate.query("SELECT people.id, people.name, people.phone, people.birthday, people.surname, people.passport FROM bookrotation INNER JOIN books ON books.id = bookrotation.idbook INNER JOIN people on people.id = bookrotation.idpeople where books.id = ?  ORDER BY bookrotation.date DESC LIMIT 1", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
}
