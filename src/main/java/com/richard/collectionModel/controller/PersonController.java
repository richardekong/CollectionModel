package com.richard.collectionModel.controller;


import com.richard.collectionModel.model.Person;
import com.richard.collectionModel.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PeopleService service;

    @Autowired
    public PersonController(PeopleService people) {
        this.service = people;
    }

    @PostMapping
    private ResponseEntity<Void> createPerson(@RequestBody Person person, UriComponentsBuilder ucb) throws Exception {
        if (service.existsByName(person.name())){
            System.out.printf("\n\n%s already exists in the database",person.name());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Person personToCreate = new Person(
                null,
                person.name(),
                person.age()
        );
        Person createdPerson = service.save(personToCreate);
        URI personLocation = ucb.path("api/people/{id}")
                .buildAndExpand(createdPerson.id())
                .toUri();
        System.out.printf("\n\nYou can locate the new Person here:%s\n\n\n", personLocation);
        return ResponseEntity.created(personLocation).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Person> findById(@PathVariable Long id) throws Exception {
        Optional<Person> optionalPerson = service.findById(id);
        if (optionalPerson.isPresent()){
            Person foundPerson = optionalPerson.get();
            System.out.printf("\n\nPerson with id number %d is:\n%s\n\n",id,foundPerson);
            return ResponseEntity.ok(foundPerson);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/average")
    private ResponseEntity<Double> findAverageAge() throws Exception {
        double averageAge = service.findAverageAge();
        System.out.printf("\n\nAverage Age is:%f\n\n",averageAge);
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/youngest")
    private ResponseEntity<Person> findYoungest() throws Exception {
        Person youngest = service.findYoungest();
        if(youngest != null){
            System.out.println(youngest);
            return ResponseEntity.ok(youngest);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/oldest")
    private ResponseEntity<Person> findOldest() throws Exception {
        Person oldestPerson = service.findOldest();
        if(oldestPerson != null){
            System.out.println(oldestPerson);
            return ResponseEntity.ok(oldestPerson);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    private ResponseEntity<List<Person>> findAll(Pageable pageInfo) throws Exception {
        PageRequest request = PageRequest.of(
                pageInfo.getPageNumber(),
                pageInfo.getPageSize(),
                pageInfo.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
        );
        Page<Person> page = service.findAll(request);
        System.out.println(page.getContent());
        return ResponseEntity.ok(page.getContent());
    }

}
