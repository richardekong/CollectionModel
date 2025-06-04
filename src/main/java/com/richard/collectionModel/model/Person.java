package com.richard.collectionModel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Person (@Id Long id, String name, int age){}

