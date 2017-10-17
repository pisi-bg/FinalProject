package model.pojo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

public class Order {

	private long orderId;
	private User user;
	private LocalDateTime dateTime;
	private double discount; // by default 0
	private double finalPrice;

	private HashMap<Product, Integer> products;

	// constructor to send info in DB
	public Order(User user, LocalDateTime datetime, double discount, double finalPrice, int deliveryInfoId,
			HashMap<Product, Integer> products) {
		this.user = user;
		this.dateTime = datetime;
		this.discount = discount;
		this.finalPrice = finalPrice;
		this.products = products;
	}

	// constructor to retrieve info from DB
	public Order(long orderId, long user_id, LocalDateTime datetime, double discount, double finalPrice,
			HashMap<Product, Integer> products) {
		this.orderId = orderId;
		this.user = user;
		this.dateTime = datetime;
		this.discount = discount;
		this.finalPrice = finalPrice;
		this.products = products;
	}

	public void setId(long orderId) {
		this.orderId = orderId;
	}

	// getters

	public User getUser() {
		return user;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public double getDiscount() {
		return discount;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public HashMap<Product, Integer> getProducts() {
		return (HashMap<Product, Integer>) Collections.unmodifiableMap(products);
	}

}
