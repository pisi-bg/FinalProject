package model.pojo;

public class Product {

	private long id;
	private String name;
	private String description;
	private double price;
	private String animal;
	private String category;
	private String brand;
	private String brandImage;
	// private String brandInfo;
	private double rating;
	private int isStock;
	private String image;
	private int discount; // in percent

	public Product(long id, String name, String description, double price, String animal, String category, String brand,
			String brandImage, double rating, int isStock, String image, int discount) {
		this(id, name, description, price, discount, animal, category, image, rating, brand);
		this.brandImage = brandImage;
		this.isStock = isStock;
	}

	// TODO removed the old one -> check if needed
	// public Product(long id, String name, String description, double price,
	// String animal, String category, String brand,
	// String manifactureInfo, double rating, int isStock, String image) {
	// this(id,name,description,price,manifactureInfo,image,rating);
	// this.animal = animal;
	// this.category = category;
	// this.brand = brand;
	// this.rating = rating;
	// this.isStock = isStock;
	// }
	// constructor to retrieve short info from DB for orders history

	public Product(long id, String name, String description, double price, int discount, String animal, String category,
			String image, double rating, String brand) {
		this(id, name, description, price, category, rating, image);
		this.discount = discount;
		this.animal = animal;
		this.brand = brand;
	}

	public Product(long id, String name, String description, double price, String category, double rating,
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

	public void setDiscount(int discount) {
		if (discount >= 0 && discount <= 100) {
			this.discount = discount;
		}
	}

	// for demo purpose
	@Override
	public String toString() {
		return this.name + " " + this.description + " " + this.animal + " " + this.category;
	}

	// getters
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getAnimal() {
		return animal;
	}

	public String getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public String getBrandImage() {
		return brandImage;
	}

	public double getRating() {
		return rating;
	}

	public int getIsStock() {
		return isStock;
	}

	public String getImage() {
		return image;
	}

	public int getDiscount() {
		return discount;
	}

}
