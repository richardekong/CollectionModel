package com.richard.collectionModel.service;

import com.richard.collectionModel.model.Person;
import com.richard.collectionModel.repository.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService implements CollectionStatistic<Person>{

    @Autowired
    private final People repo;

    public PeopleService(People repo) {
        this.repo = repo;
    }

    public Person save(Person person) throws Exception{
        return repo.save(person);
    }

    public Iterable<Person> saveAll(List<Person> people) throws Exception{
        return repo.saveAll(people);
    }

    public Optional<Person> findById(Long id) throws  Exception{
        return repo.findById(id);
    }

    public List<Person> findAll() throws Exception{
        return (List<Person>) repo.findAll();
    }

    public Page<Person> findAll(Pageable pageable) throws Exception{
        return repo.findAll(pageable);
    }

    public boolean existsByName(String name) throws Exception{
        return repo.existsByName(name);
    }


    @Override
    public double findAverageAge() {
        int numberOfPeople = 0;
        double totalAge = 0;
        double average = 0.0;
        for (Person person : repo.findAll()) {
            numberOfPeople++;
            totalAge += person.age();
        }
        if (numberOfPeople == 0) {
            return average;
        }
        average = totalAge / numberOfPeople;
        return  average;
    }

    @Override
    public Person findOldest() {
        Person oldestPerson = null;
        for (Person person : repo.findAll()){
            if (oldestPerson == null || oldestPerson.age() < person.age())
                oldestPerson = person;
        }
        return oldestPerson;
    }

    @Override
    public Person findYoungest() {
        Person youngestPerson = null;
        for (Person person : repo.findAll()){
            if (youngestPerson == null || youngestPerson.age() > person.age())
                youngestPerson = person;
        }
        return youngestPerson;
    }
}
