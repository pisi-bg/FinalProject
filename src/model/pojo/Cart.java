package model.pojo;

import java.util.HashMap;
import java.util.Map.Entry;

public class Cart {

	private static Cart instance;

	private Cart() {
	}

	public static synchronized Cart getInstance() {
		if (instance == null) {
			instance = new Cart();
		}
		return instance;
	}

	public static double calcProductPricePerQuantity(Product product, int quantity) {
		double productPrice = product.getPrice();
		if (product.getDiscount() != 0) {
			productPrice = product.calcDiscountedPrice();
		}
		return productPrice * quantity;
	}

	public static double calculatePriceForCart(HashMap<Product, Integer> products) {
		double price = 0;
		for (Entry<Product, Integer> entry : products.entrySet()) {
			Product product = entry.getKey();
			int quantity = (int) entry.getValue();
			double productPrice = product.getPrice();
			if (product.getDiscount() != 0) {
				productPrice = product.calcDiscountedPrice();
			}
			price += (product.getPrice() * quantity);
		}
		return price;
	}

}
