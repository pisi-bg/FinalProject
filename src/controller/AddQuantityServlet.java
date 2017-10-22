package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDao;
import model.pojo.Product;

@WebServlet("/addquantity")
public class AddQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		if(quantity <= 0 || request.getSession().getAttribute("productCurrent") == null){
			response.setStatus(400);			
		}else {
			Product p = (Product) request.getSession().getAttribute("productCurrent");			
			try {
				ProductDao.getInstance().addQuantity(p.getId(), quantity);
			} catch (SQLException e) {
				// TODO redirect to error page
				e.printStackTrace();
			}	
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}
