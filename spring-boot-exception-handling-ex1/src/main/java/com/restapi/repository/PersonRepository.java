package com.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restapi.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

}
