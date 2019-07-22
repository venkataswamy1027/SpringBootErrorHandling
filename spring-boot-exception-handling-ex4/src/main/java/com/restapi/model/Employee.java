package com.restapi.model;

import lombok.Data;

@Data
public class Employee {
	
	private String empId;
	private String name;
	private String designation;
	private double salary;
}
