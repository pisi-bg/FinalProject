package model.pojo;

import java.util.Set;

public class User {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean isMale;
	private boolean isAdmin;
	private Set<Product> favorites;

	// constructor with email and pass for login
	public User(String email, String pass) {
		this.email = email;
		this.password = pass;
	}

	// constructor with all fields
	public User(long id, String firstName, String lastName, String email, String password, boolean isMale,
			boolean isAdmin, Set<Product> favorites) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isMale = isMale;
		this.isAdmin = isAdmin;
		this.favorites = favorites;
	}

	// constructor for register
	public User(String firstName, String lastName, String email, String password, boolean isMale) {
		this(email, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.isMale = isMale;
		this.isAdmin = false;
	}

	// check if user is admin
	public boolean isAdmin() {
		return isAdmin;
	}

	// return user email
	public String getEmail() {
		return email;
	}

	// return user password
	public String getPassword() {
		return password;
	}

	// return true if user is male and false if user is female
	public boolean isMale() {
		return this.isMale;
	}

	// return user first name
	public String getFirstName() {
		return firstName;
	}

	// return user last name
	public String getLastName() {
		return lastName;
	}

	// set id which is returned by the database
	public void setId(long id) {
		this.id = id;
	}

	// returns user id
	public long getId() {
		return id;
	}

	// add favorites after adding it to DB
	public void addToFavorites(Product p) {
		this.favorites.add(p);
	}

	public void removeFromFavorites(Product p) {
		this.favorites.remove(p);
	}

	public boolean hasInFavorites(Product p) {
		if (p != null) {
			return favorites.contains(p);
		}
		return false;
	}

}
