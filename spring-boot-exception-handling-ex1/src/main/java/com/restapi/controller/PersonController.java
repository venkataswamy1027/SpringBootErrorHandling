package com.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.Person;
import com.restapi.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@GetMapping("/persons")
	public List<Person> getAllPersons() {
		LOGGER.info("getAllPersons method executed");
		return personService.list();
	}

	@GetMapping("/person/{personId}")
	public Person getPerson(@PathVariable int personId) {
		LOGGER.info("getPerson method executed");
		return personService.getPersonById(personId);
	}

	@PostMapping("/person")
	public Person addPerson(@Validated @RequestBody Person person) {
		LOGGER.info("addPerson method executed");
		return personService.createPerson(person);
	}

	@PutMapping("/person/{personId}")
	public Person updatePerson(@RequestBody Person person, @PathVariable int personId) {
		LOGGER.info("updatePerson method executed");
		return personService.update(person, personId);
	}

	@DeleteMapping("/person/{personId}")
	public String deletePerson(@PathVariable int personId) {
		LOGGER.info("deletePerson method executed");
		personService.delete(personId);
		return "person " + personId + " deleted successfully.";
	}

}
