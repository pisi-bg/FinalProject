package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDao;
import model.pojo.Product;


@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		int category = Integer.parseInt(request.getParameter("id"));
		int animal = Integer.parseInt(request.getParameter("animal"));
		Map<String, List<Product>> products = new HashMap<>();
		
		try {
			products = ProductDao.getInstance().getProductsByAnimalAndParentCategory(animal,category);
			request.getSession().setAttribute("productsForCategory", products);
			request.setAttribute("id", category); // in request so every time you change animal category sub-categories will be not remembered
		} catch (SQLException e) {
			// TODO re-direct to error page and re-throw e;
			e.printStackTrace();
		}
		request.getRequestDispatcher("parentCategory.jsp").forward(request, response);
	}

}
