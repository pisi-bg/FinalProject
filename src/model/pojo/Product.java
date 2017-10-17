package model.pojo;

import java.util.List;

public class Product {

	private long id;
	private String name;
	private String description;
	private double price;
	private String animal;
	private String category;
	private String brand;
	private String brandInfo;
	private double rating;
	private int isStock;
	private String image;

	public Product(long id, String name, String description, double price, String animal, String category, String brand,
			String manifactureInfo, double rating, int isStock, String image) {
		this(id,name,description,price,manifactureInfo,image,rating);
		this.animal = animal;
		this.category = category;
		this.brand = brand;
		this.rating = rating;
		this.isStock = isStock;
	}

	// constructor to retrieve short info from DB for orders history
	public Product(long id, String name, String description, double price, String manifactureInfo, String image, double rating) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.brandInfo = manifactureInfo;
		this.rating = rating;
		this.image = image;
	}
	
		
	public Product(long id, String name, String description, double price, String category,  double rating,
			String image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.rating = rating;
		this.image = image;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	// for demo purpose 
	@Override
	public String toString() {
		return this.name + " " + this.description + " " + this.animal + " " + this.category;
	}
}
