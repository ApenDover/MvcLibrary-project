package ru.tsandrey.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tsandrey.model.Author;
import ru.tsandrey.model.Book;
import ru.tsandrey.model.Person;
import ru.tsandrey.model.SearchParams;

import java.sql.Date;
import java.util.List;

@Component
public class AuthorDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> bookList(int id)
    {
        return jdbcTemplate.query("SELECT * FROM books WHERE idauthor = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Author> authorList()
    {
        return jdbcTemplate.query("SELECT * FROM author", new BeanPropertyRowMapper<>(Author.class));
    }

    public Author getAuthor(int id) {
        return jdbcTemplate.query("SELECT * FROM author WHERE author.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Author.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id, Author author) {
        Date birthday = Date.valueOf(author.getBirthday());
        jdbcTemplate.update("UPDATE author SET name = ?, surname = ?, birthday = ? WHERE author.id = ?", author.getName(), author.getSurname(), birthday, id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM author WHERE author.id = ?", new Object[]{id});
    }

    public void save(Author author) {
        Date birthday = Date.valueOf(author.getBirthday());
        jdbcTemplate.update("INSERT INTO author(name, surname, birthday) VALUES (?, ?, ?)", author.getName(), author.getSurname(), birthday);
    }

    public List<Author> findAuthor(SearchParams searchParams) {
        String param = "%" + searchParams.getSearchString() + "%";
        if (searchParams.getIdSearch() == 1) {
            return jdbcTemplate.query("SELECT * FROM author WHERE author.name ~~* ? OR author.surname ~~* ?", new Object[]{param, param}, new BeanPropertyRowMapper<>(Author.class));
        }
        if (searchParams.getIdSearch() == 2) {
            return jdbcTemplate.query("SELECT author.id, author.name, author.surname, author.birthday FROM author INNER JOIN books ON books.idauthor = author.id WHERE books.name LIKE ? GROUP BY author.id", new Object[]{param}, new BeanPropertyRowMapper<>(Author.class));
        }
        return null;
    }
}
