package com.restapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Item {
	@Id
	@GeneratedValue
	private int id;
	private String name;
    private String category;
}
