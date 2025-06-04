package com.richard.collectionModel.service;

import java.util.List;

public interface CollectionStatistic<T> {

     double findAverage(List<T> collection);
     T findOldest(T t);
     T findYoungest(T t);

}
