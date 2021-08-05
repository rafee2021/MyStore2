package com.product.model;

public class Product {

	private int id;
	private String brand;
	private String name;
	private String quantity;

	public Product(int id, String brand, String name, String quantity) {
		super();
		this.id = id;
		this.brand = brand;
		this.name = name;
		this.quantity = quantity;
	}

	public Product(String brand, String name, String quantity) {
		super();
		this.brand = brand;
		this.name = name;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
