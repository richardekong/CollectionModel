package com.richard.collectionModel.repository;

import com.richard.collectionModel.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface People extends CrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long> {
}
