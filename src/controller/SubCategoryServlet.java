package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDao;
import model.pojo.Product;


@WebServlet("/subcategory")
public class SubCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int animal = Integer.parseInt(request.getParameter("animal"));
		int id = Integer.parseInt(request.getParameter("subId"));
		Object temp = request.getAttribute("id"); // idea of this is to be set in request so when subCategories.jsp check for
													//id to represent matching sub-categories, unfortunately it didn't work .... CHECK IT LATER !!! 
		
		
		try {
			List<Product> products = ProductDao.getInstance().getProductsByAnimalAndSubCategory(animal, id);
			request.getSession().setAttribute("products", products);
			request.setAttribute("id", temp);
		} catch (SQLException e) {
			// TODO redirect to error page and re-throw e;
			e.printStackTrace();
		}
		request.getRequestDispatcher("subCategory.jsp").forward(request, response);
	}

}
