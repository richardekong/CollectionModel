package com.richard.collectionModel.controller;


import com.richard.collectionModel.model.Person;
import com.richard.collectionModel.repository.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    private People people;

    @Autowired
    public PersonController(People people) {
        this.people = people;
    }

    private ResponseEntity<Void> createPerson(@RequestBody Person person, UriComponentsBuilder ucb){
        if (people.existsByName(person.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Person personToCreate = new Person(
                null,
                person.getName(),
                person.getAge()
        );
        Person createdPerson = people.save(personToCreate);
        URI personLocation = ucb.path("api/people/{id}")
                .buildAndExpand(createdPerson.getId())
                .toUri();
        return ResponseEntity.created(personLocation).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Person> findById(@PathVariable Long id){
        Optional<Person> optionalPerson = people.findById(id);
        if (optionalPerson.isPresent()){
            Person foundPerson = optionalPerson.get();
            return ResponseEntity.ok(foundPerson);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping ResponseEntity<Person> findByName(@PathVariable String name){
        Optional<Person> optionalPerson = people.findByName(name);
        if(optionalPerson.isPresent()){
            Person foundPerson = optionalPerson.get();
            return ResponseEntity.ok(foundPerson);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    private ResponseEntity<Double> findAverageAge(){
        Iterable<Person> personIterator = people.findAll();

        List<Person> collection = getPeople(personIterator);
        double averageAge = people.findAverageAge(collection);
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping
    private ResponseEntity<Person> findYoungest(){
        List<Person> collection = getPeople(people.findAll());
        Person youngest = people.findYoungest(collection);
        if(youngest != null){
            return ResponseEntity.ok(youngest);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    private ResponseEntity<Person> findOldest(){
        List<Person> collection = getPeople(people.findAll());
        Person oldestPerson = people.findOldest(collection);
        if(oldestPerson != null){
            return ResponseEntity.ok(oldestPerson);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    private ResponseEntity<List<Person>> findAll(Pageable pageInfo){
        PageRequest request = PageRequest.of(
                pageInfo.getPageNumber(),
                pageInfo.getPageSize(),
                pageInfo.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
        );
        Page<Person> page = people.findAll(request);
        return ResponseEntity.ok(page.getContent());
    }

    private List<Person> getPeople(Iterable<Person> personIterator) {
        List<Person> collection = new ArrayList<>();
        while (personIterator.iterator().hasNext()){
            collection.add(personIterator.iterator().next());
        }
        return collection;
    }

}
