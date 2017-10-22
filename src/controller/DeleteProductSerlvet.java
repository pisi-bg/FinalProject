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


@WebServlet("/delete")
public class DeleteProductSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Product pro = (Product)request.getSession().getAttribute("productCurrent");
		try {
			ProductDao.getInstance().removeProduct(pro);
		} catch (SQLException e) {
			// TODO error page
			e.printStackTrace();
		}
		request.getSession().removeAttribute("productCurrent");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
