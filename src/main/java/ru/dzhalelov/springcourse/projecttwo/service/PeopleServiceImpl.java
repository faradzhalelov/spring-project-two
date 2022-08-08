package ru.dzhalelov.springcourse.projecttwo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dzhalelov.springcourse.projecttwo.model.Book;
import ru.dzhalelov.springcourse.projecttwo.model.Person;
import ru.dzhalelov.springcourse.projecttwo.repository.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleServiceImpl implements PeopleService
{

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServiceImpl(PeopleRepository peopleRepository)
    {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public List<Person> findAll()
    {
        return peopleRepository.findAll();
    }

    @Override
    public Person findOne(int id)
    {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Override
    @Transactional
    public void save(Person person)
    {
        peopleRepository.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson)
    {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Override
    @Transactional
    public void delete(int id)
    {
        peopleRepository.deleteById(id);
    }

    @Override
    public Optional<Person> getPersonByFullName(String fullName)
    {
        return peopleRepository.findByFullName(fullName);
    }

    @Override
    public List<Book> getBooksByPersonId(int id)
    {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent())
        {
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book ->
            {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                // 864000000 милисекунд = 10 суток
                if (diffInMillies > 864000000)
                    book.setExpired(true); // книга просрочена
            });

            return person.get().getBooks();
        }
        else
        {
            return Collections.emptyList();
        }
    }
}
