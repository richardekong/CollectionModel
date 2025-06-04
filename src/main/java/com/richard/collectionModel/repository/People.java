package com.richard.collectionModel.repository;

import com.richard.collectionModel.model.Person;
import com.richard.collectionModel.service.CollectionStatistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface People extends CrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long>, CollectionStatistic<Person> {

    @Override
    default double findAverageAge(List<Person> collection) {
        int numberOfPeople = collection.size();
        double totalAge = 0;
        for (Person person : collection){
            totalAge += person.getAge();
        }
        return totalAge / numberOfPeople;
    }

    @Override
    default Person findOldest(List<Person> collection) {
        Person oldestPerson = new Person(null, "", Integer.MIN_VALUE);
        for (Person person : collection){
            if (person!=null && (oldestPerson.getAge() < person.getAge())){
                oldestPerson.setId(person.getId());
                oldestPerson.setName(person.getName());
                oldestPerson.setAge(person.getAge());
            }
        }
        return oldestPerson;
    }

    @Override
    default Person findYoungest(List<Person> collection) {
        Person oldestPerson = new Person(null, "", Integer.MAX_VALUE);
        for (Person person : collection){
            if (person!=null && (oldestPerson.getAge() > person.getAge())){
                oldestPerson.setId(person.getId());
                oldestPerson.setName(person.getName());
                oldestPerson.setAge(person.getAge());
            }
        }
        return oldestPerson;
    }

    Optional<Person> findByName(String name);

    boolean existsByName(String person);
}
