package ru.dzhalelov.springcourse.projecttwo.service;

import ru.dzhalelov.springcourse.projecttwo.model.Book;
import ru.dzhalelov.springcourse.projecttwo.model.Person;

import java.util.List;

public interface BooksService
{
    List<Book> findAll(boolean sortByYear);
    List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear);
    Book findOne(int id);
    List<Book> searchByTitle(String query);
    void save(Book book);
    void update(int id, Book updatedBook);
    void delete(int id);
    Person getBookOwner(int id);
    void release(int id);
    void assign(int id, Person selectedPerson);
}
