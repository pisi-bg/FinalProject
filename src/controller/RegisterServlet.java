package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserDAO;
import model.pojo.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	// this method check if register form is correctly filled and then push data to UserDAO 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		if(firstName.isEmpty()){
			response.getWriter().append("First name is empty.");
			return;
		}
		if(lastName.isEmpty()){
			response.getWriter().append("Last name is empty.");
			return;
		}
		if(!UserDAO.isValidEmailAddress(email)){
			response.getWriter().append("Invalid email.");
			return;
		}
		if(password.isEmpty()){
			response.getWriter().append("Invalid password.");
			return;
		}

		User u = new User(firstName, lastName, email, password, gender.equals("male") ? true : false);
		
		try {
			if(UserDAO.getInstance().userExist(u)){
				response.getWriter().append("Email exist.");
				return;
			}
			UserDAO.getInstance().insertUser(u);
		} catch (SQLException e) {
			
			// TODO redirect to error page
			
			response.getWriter().append("something in SQL DBManager" + e.getMessage());
		}
		
		//TODO update session, so after register you remain logged in
		
	}
	
}
