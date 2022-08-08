package ru.dzhalelov.springcourse.projecttwo.service;

import ru.dzhalelov.springcourse.projecttwo.model.Book;
import ru.dzhalelov.springcourse.projecttwo.model.Person;

import java.util.List;
import java.util.Optional;

public interface PeopleService
{
    List<Person> findAll();
    Person findOne(int id);
    void save(Person person);
    void update(int id, Person updatedPerson);
    void delete(int id);
    Optional<Person> getPersonByFullName(String fullName);
    List<Book> getBooksByPersonId(int id);
}
