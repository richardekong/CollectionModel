package com.richard.collectionModel.controller;


import com.richard.collectionModel.repository.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/person")
public class PersonController {

    private People people;

    @Autowired
    public PersonController(People people) {
        this.people = people;
    }




}
