package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import model.dao.ProductDao;
import model.pojo.Product;


@WebServlet("/search")
public class SearchProductService extends HttpServlet {
	private static final long serialVersionUID = 1L;
   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		

		System.out.println("ko staa");
		
		String[] arr = request.getParameter("q").split(" ");
		
		
		System.out.println(arr.length);
		
		
		
		
		List<Product> result = null;
		try {
			result = ProductDao.getInstance().searchProductByWord(arr);
			JsonArray jarr = new JsonArray();
			Gson gs = new Gson();
			
			
			System.out.println("ehpooooo");
			
			
			
			System.out.println(result.size());
			
			for(Product p : result){			
				jarr.add(gs.toJson(p));
				
				
				System.out.println(gs.toJson(p).toString());
				
				
			}
			
//			String s = new String(jarr.toString().getBytes(), "UTF-8");  =====  doesn't work!!!
			
			response.getWriter().append(jarr.toString());
		} catch (SQLException e) {
			System.out.println("error " +e.getMessage());
			response.setStatus(400);
			// or redirect to error page;
		}
		
	}	
}

