package ru.dzhalelov.springcourse.projecttwo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dzhalelov.springcourse.projecttwo.model.Book;
import ru.dzhalelov.springcourse.projecttwo.model.Person;
import ru.dzhalelov.springcourse.projecttwo.repository.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksServiceImpl implements BooksService
{
    private final BooksRepository booksRepository;

    @Autowired
    public BooksServiceImpl(BooksRepository booksRepository)
    {
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Book> findAll(boolean sortByYear)
    {
        if (sortByYear)
        {
            return booksRepository.findAll(Sort.by("year"));
        }

        else
        {
            return booksRepository.findAll();
        }
    }

    @Override
    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear)
    {
        if (sortByYear)
        {
            return booksRepository
                    .findAll(PageRequest.of(page, booksPerPage, Sort.by("year")))
                    .getContent();
        }
        else
        {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    @Override
    public Book findOne(int id)
    {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Override
    public List<Book> searchByTitle(String query)
    {
        return booksRepository.findByTitleStartingWith(query);
    }

    @Override
    @Transactional
    public void save(Book book)
    {
        booksRepository.save(book);
    }

    @Override
    @Transactional
    public void update(int id, Book updatedBook)
    {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public void delete(int id)
    {
        booksRepository.deleteById(id);
    }

    @Override
    public Person getBookOwner(int id)
    {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Override
    @Transactional
    public void release(int id)
    {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

    @Override
    @Transactional
    public void assign(int id, Person selectedPerson)
    {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                }
        );
    }
}
