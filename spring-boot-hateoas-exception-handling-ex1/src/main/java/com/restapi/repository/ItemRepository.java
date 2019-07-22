package com.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restapi.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

}
