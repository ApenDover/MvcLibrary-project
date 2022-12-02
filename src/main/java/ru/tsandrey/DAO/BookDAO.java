package ru.tsandrey.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tsandrey.model.Author;
import ru.tsandrey.model.Book;
import ru.tsandrey.model.JoinBook;
import ru.tsandrey.model.SearchParams;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, List<Book> bookList) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<JoinBook> allBooks() {
        return jdbcTemplate.query("SELECT books.id as id, books.name as nameBook, books.year as yearBook, author.name as nameAuthor, author.surname as surnameAuthor FROM books INNER JOIN author ON books.idauthor = author.id", new BeanPropertyRowMapper<>(JoinBook.class));
    }

    public JoinBook joinBookById(int id) {
        return jdbcTemplate.query("SELECT books.id as id, author.id as idAuthor, books.name as nameBook, books.year as yearBook, author.name as nameAuthor, author.surname as surnameAuthor FROM books INNER JOIN author ON books.idauthor = author.id WHERE books.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(JoinBook.class)).stream().findAny().orElse(null);
    }


    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO books(name, year, idauthor) VALUES(?, ?, ?)", book.getName(), book.getYear(), book.getIdAuthor());
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(Book book, int id) {
        jdbcTemplate.update("UPDATE books SET name = ?, idauthor = ?, year = ? WHERE books.id = ?", book.getName(), book.getIdAuthor(), book.getYear(), id);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    public List<Author> getAuthorList() {
        return jdbcTemplate.query("SELECT * FROM author", new BeanPropertyRowMapper<>(Author.class));
    }

    public List<Author> getAuthorListById(int authorId) {
        return jdbcTemplate.query("SELECT * FROM author WHERE id = ?", new Object[]{authorId}, new BeanPropertyRowMapper<>(Author.class));
    }

    public Author getAuthor(int id) {
        return jdbcTemplate.query("SELECT * FROM author WHERE author.id = (SELECT idauthor FROM books WHERE id = ?)", new Object[]{id}, new BeanPropertyRowMapper<>(Author.class)).stream().findAny().orElse(null);
    }

    public List<JoinBook> findBooks(SearchParams searchParams) {
        if (searchParams.getIdSearch() == 1) {
            String findParam = "%" + searchParams.getSearchString() + "%";
            return jdbcTemplate.query("SELECT books.id as id, author.id as idAuthor, books.name as nameBook, books.year as yearBook, author.name as nameAuthor, author.surname as surnameAuthor FROM books INNER JOIN author ON books.idauthor = author.id WHERE author.name ~~* ?", new Object[]{findParam}, new BeanPropertyRowMapper<>(JoinBook.class));
        }
        if (searchParams.getIdSearch() == 2) {
            String findParam = "%" + searchParams.getSearchString() + "%";
            return jdbcTemplate.query("SELECT books.id as id, author.id as idAuthor, books.name as nameBook, books.year as yearBook, author.name as nameAuthor, author.surname as surnameAuthor FROM books INNER JOIN author ON books.idauthor = author.id WHERE books.name ~~* ?", new Object[]{findParam}, new BeanPropertyRowMapper<>(JoinBook.class));
        }
        if (searchParams.getIdSearch() == 3) {
            try {
                int findParam = Integer.parseInt(searchParams.getSearchString());
                return jdbcTemplate.query("SELECT books.id as id, author.id as idAuthor, books.name as nameBook, books.year as yearBook, author.name as nameAuthor, author.surname as surnameAuthor FROM books INNER JOIN author ON books.idauthor = author.id WHERE books.year = ?", new Object[]{findParam}, new BeanPropertyRowMapper<>(JoinBook.class));
            } catch (Exception e) {
                return jdbcTemplate.query("SELECT books.id as id, author.id as idAuthor, books.name as nameBook, books.year as yearBook, author.name as nameAuthor, author.surname as surnameAuthor FROM books INNER JOIN author ON books.idauthor = author.id", new BeanPropertyRowMapper<>(JoinBook.class));
            }


        }
        return null;
    }

}
