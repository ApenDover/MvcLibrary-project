package ru.tsandrey.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tsandrey.model.Person;
import ru.tsandrey.model.SearchParams;

import java.sql.Date;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, List<Person> personList) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> peopleList() {
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM people WHERE people.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id, Person person) {
        Date birthday = Date.valueOf(person.getBirthday());
        jdbcTemplate.update("UPDATE people SET passport = ?, name = ?, surname = ?, phone = ?, birthday = ? WHERE people.id = ?", person.getPassport(), person.getName(), person.getSurname(), person.getPhone(), birthday, id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM people WHERE people.id = ?", new Object[]{id});
    }

    public void save(Person person) {
        Date birthday = Date.valueOf(person.getBirthday());
        jdbcTemplate.update("INSERT INTO People(passport, name, surname, phone, birthday) VALUES (?, ?, ?, ?, ?)", person.getPassport(), person.getName(), person.getSurname(), person.getPhone(), birthday);
    }

    public List<Person> findPerson(SearchParams searchParams) {
        String param = "%" + searchParams.getSearchString() + "%";
        if (searchParams.getIdSearch() == 1) {
            return jdbcTemplate.query("SELECT * FROM people WHERE passport ~~* ?", new Object[]{param}, new BeanPropertyRowMapper<>(Person.class));
        }
        if (searchParams.getIdSearch() == 2) {
            return jdbcTemplate.query("SELECT * FROM people WHERE name ~~* ? OR surname ~~* ?", new Object[]{param, param}, new BeanPropertyRowMapper<>(Person.class));
        }
        if (searchParams.getIdSearch() == 3) {
            try {
                return jdbcTemplate.query("SELECT * FROM people WHERE birthday ~~* ?", new Object[]{searchParams.getSearchString()}, new BeanPropertyRowMapper<>(Person.class));
            } catch (Exception e) {
                return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
            }
        }
        if (searchParams.getIdSearch() == 4) {
            return jdbcTemplate.query("SELECT * FROM people WHERE phone ~~* ?", new Object[]{param}, new BeanPropertyRowMapper<>(Person.class));
        }
        return null;
    }
}
