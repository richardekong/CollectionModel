package com.richard.collectionModel.repository;

import com.richard.collectionModel.model.Person;
import com.richard.collectionModel.service.CollectionStatistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface People extends CrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long>{

//    @Override
//    default Person findOldest(Iterable<Person> collection) {
//        Person oldestPerson = null;
//        for (Person person : collection) {
//            if (oldestPerson == null || oldestPerson.age() < person.age()) {
//                oldestPerson = person;
//            }
//        }
//        return oldestPerson;
//    }
//
//    @Override
//    default Person findYoungest(Iterable<Person> collection) {
//        Person youngestPerson = null;
//        for (Person person : collection) {
//            if (youngestPerson == null || youngestPerson.age() > person.age()) {
//                youngestPerson = person;
//            }
//        }
//        return youngestPerson;
//    }

    boolean existsByName(String person);
}
