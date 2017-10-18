package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AnimalDao;
import model.dao.ProductDao;
import model.pojo.Product;


@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("animal") == null){			
			request.getRequestDispatcher("products.jsp").forward(request, response);
			return;
		}
		
		String animal = request.getParameter("animal");
		int animalID = getAnimalIdx(animal);  // only for demo purpose !!!
		
		try {			
			HashMap<String, ArrayList<Product>> products = ProductDao.getInstance().getProductsByAnimal(animalID);
			request.getSession().setAttribute("products", products);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("products.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private int getAnimalIdx(String animal){
	
		switch (animal) {
		case "aqua":
			return 1;
		case "cat":
			return 2;
		case "dog":
			return 3;
		case "little":
			return 4;
		case "bird":
			return 5;
		case "reptile":
			return 6;
		default:
			return 0;
		}
	}

}
