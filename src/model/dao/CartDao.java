package model.dao;

import java.util.HashMap;
import java.util.Map.Entry;

import model.pojo.Product;

public class CartDao {

	private static CartDao instance;

	private CartDao() {
	}

	public static synchronized CartDao getInstance() {
		if (instance == null) {
			instance = new CartDao();
		}
		return instance;
	}

	// public static double calcProductPricePerQuantity(Product product, int
	// quantity) {
	// double productPrice = product.getPrice();
	// if (product.getDiscount() != 0) {
	// productPrice = product.calcDiscountedPrice();
	// }
	// return productPrice * quantity;
	// }

	public double calculatePriceForCart(HashMap<Product, Integer> products) {
		if (products != null) {
			double cartPrice = 0;
			for (Entry<Product, Integer> entry : products.entrySet()) {
				Product product = entry.getKey();
				int quantity = (int) entry.getValue();
				double productPrice = product.getPrice();
				if (product.getDiscount() != 0) {
					productPrice = product.calcDiscountedPrice();
				}
				cartPrice += (productPrice * quantity);
			}
			return cartPrice;
		} else
			// throw exception
			return 0;

	}

	// public static void main(String[] args) throws SQLException {
	// HashMap<Product, Integer> prod = new HashMap<>();
	// for (int i = 0; i < 5; i++) {
	// Product p = ProductDao.getInstance().getProduct(i);
	// prod.put(p, 3 + i);
	// }
	// System.out.println(CartDao.getInstance().calculatePriceForCart(prod));
	// }

}
