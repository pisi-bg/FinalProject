package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.dao.ProductDao;
import model.pojo.Product;


@WebServlet("/addProduct")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
	private static final String IMAGE_URL = "D://images/";
     
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		request.setCharacterEncoding("UTF-8");

		
		String name = request.getParameter("name");
		String animal = request.getParameter("animal");
		String category = request.getParameter("category");
		double price = Double.parseDouble(request.getParameter("price"));
		String description = request.getParameter("description");
		String brand = request.getParameter("brand");
		int instock = Integer.parseInt(request.getParameter("instock_count"));
		int discount = Integer.parseInt(request.getParameter("discount"));
				
		String imageFileName = name.replaceAll(" ",	""); // consider using some regex to escape sequence in file system
		Part image = request.getPart("image");
		InputStream fis = image.getInputStream();
		File imageFile = new File(IMAGE_URL + imageFileName + ".jpg");
		
		
		if(!imageFile.exists()){
			imageFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(imageFile);
		
		int b = fis.read();
		while(b != -1){
			fos.write(b);
			b = fis.read();
		}
		
		fis.close();
		fos.close();
		
		String imageURL = imageFileName.concat(".jpg");		

		Product p = new Product();
		p.setName(name);
		p.setAnimal(animal);
		p.setCategory(category);
		p.setPrice(price);
		p.setDescription(description);
		p.setBrand(brand);
		p.setInStock(instock);
		p.setDiscount(discount);
		p.setImage(imageURL);
		
		try {
			ProductDao.getInstance().addProduct(p);
		} catch (SQLException e) {
			// TODO request.getRequestDispatcher("error.jsp").forward(request, response);
			System.out.println(e.getMessage());
		}
		request.getRequestDispatcher("products.jsp").forward(request, response);
	}
}
