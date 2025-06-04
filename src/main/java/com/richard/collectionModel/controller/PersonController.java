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

    private final People people;

    @Autowired
    public PersonController(People people) {
        this.people = people;
    }

    @PostMapping
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

    @GetMapping("/average")
    private ResponseEntity<Double> findAverageAge(){
        double averageAge = people.findAverageAge(people.findAll());
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/youngest")
    private ResponseEntity<Person> findYoungest(){
        Iterable<Person> collection = people.findAll();
        Person youngest = people.findYoungest(collection);
        if(youngest != null){
            return ResponseEntity.ok(youngest);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/oldest")
    private ResponseEntity<Person> findOldest(){
        Iterable<Person> collection = people.findAll();
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


}
