package com.richard.collectionModel.service;

public interface CollectionStatistic<T> {

     double findAverageAge();
     T findOldest();
     T findYoungest();

}
