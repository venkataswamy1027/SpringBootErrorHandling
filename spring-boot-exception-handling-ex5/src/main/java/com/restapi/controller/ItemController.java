package com.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.exception.ItemNotFoundException;
import com.restapi.model.Item;
import com.restapi.service.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemService itemService;

	@GetMapping("/getAllItems")
	public ResponseEntity<List<Item>> getAllItems() {
		LOGGER.info("getAllItems method executed");
		List<Item> items = itemService.list();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping("/item/{itemId}")
	public ResponseEntity<Item> getItem(@PathVariable int itemId) {
		LOGGER.info("getItem method executed");
		if (itemId <= 0)
			throw new ItemNotFoundException("Invalid ItemId");

		Item item = itemService.getItemById(itemId);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@PostMapping("/item/add")
	public Item addItem(@Validated @RequestBody Item item) {
		LOGGER.info("addItem method executed");
		return itemService.createItem(item);
	}

	@PutMapping("/item/{itemId}")
	public Item updateItem(@RequestBody Item item, @PathVariable int itemId) {
		LOGGER.info("updateItem method executed");
		return itemService.update(item, itemId);
	}

	@DeleteMapping("/item/{itemId}")
	public String deleteItem(@PathVariable int itemId) {
		LOGGER.info("deleteItem method executed");
		itemService.delete(itemId);
		return "item " + itemId + " deleted successfully.";
	}

}
