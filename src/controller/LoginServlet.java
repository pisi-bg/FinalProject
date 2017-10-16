package controller;

import java.io.IOException;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserDAO;
import model.pojo.User;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	//this method check if login form is correctly filled and then check if user exist in the database
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(!UserDAO.isValidEmailAddress(email)){
			response.getWriter().append("Invalid email");
			return;
		}
		if(password.isEmpty()){
			response.getWriter().append("Empty password");
			return;
		}
		
		User u = new User( email, password);
		try {
			if(UserDAO.getInstance().userExist(u)){
				if(UserDAO.getInstance().verifiedPassword(u)){
					response.getWriter().append("Login complete");
					
					//TODO update session to remain logged in and 
					
				}else {
					response.getWriter().append("Wrong password");
				}
			}else {
				response.getWriter().append("User not exist");
			}
		} catch (SQLException e) {
			
		}
		
	}

	
	

}
