package ru.dzhalelov.springcourse.projecttwo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dzhalelov.springcourse.projecttwo.model.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer>
{
    List<Book> findByTitleStartingWith(String query);
}
