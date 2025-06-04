package com.richard.collectionModel.service;

import java.util.List;

public interface CollectionStatistic<T> {

     double findAverageAge(List<T> collection);
     T findOldest(List<T> collection);
     T findYoungest(List<T> collection);

}
