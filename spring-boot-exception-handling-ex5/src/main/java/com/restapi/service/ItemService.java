package com.restapi.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.exception.ItemNotFoundException;
import com.restapi.model.Item;
import com.restapi.repository.ItemRepository;

@Service
public class ItemService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
	@Autowired
	private ItemRepository itemRepository;

	public List<Item> list() {
		LOGGER.info("list method executed");
		return (List<Item>) itemRepository.findAll();
	}

	public Item getItemById(int personId) {
		LOGGER.info("getPersonById method executed");
		Optional<Item> data = itemRepository.findById(personId);
		if (data.isPresent())
			return data.get();
		else
			throw new ItemNotFoundException("Record Not Found");
	}

	public Item createItem(Item person) {
		LOGGER.info("createItem method executed");
		return itemRepository.save(person);
	}

	public Item update(Item person, int personId) {
		LOGGER.info("update method executed");
		person.setId(personId);
		return itemRepository.save(person);
	}

	public void delete(int personId) {
		LOGGER.info("update method executed");
		itemRepository.deleteById(personId);
	}
}
