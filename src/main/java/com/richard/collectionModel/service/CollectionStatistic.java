package com.richard.collectionModel.service;

import java.util.List;

public interface CollectionStatistic<T> {

     double findAverageAge(Iterable<T> collection);
     T findOldest(Iterable<T> collection);
     T findYoungest(Iterable<T> collection);

}
