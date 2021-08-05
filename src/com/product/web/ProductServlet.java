package com.product.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.dao.ProductDao;
import com.product.model.Product;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductDao productDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			try {
				insertProduct(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/delete":
			try {
				deleteProduct(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateProduct(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			try {
				listProduct(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
		dispatcher.forward(request, response);
	}

	private void insertProduct(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException  {
		String brand = request.getParameter("brand");
		String name = request.getParameter("name");
		String quantity = request.getParameter("quantity");
		Product newProduct = new Product(brand, name, quantity);

		productDao.insertProduct(newProduct);
		response.sendRedirect("list");

	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			productDao.deleteProduct(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));

		Product existingProduct;
		try {
			existingProduct = productDao.selectProduct(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
			request.setAttribute("product", existingProduct);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void updateProduct(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		String brand = request.getParameter("brand");
		String name = request.getParameter("name");
		String quantity = request.getParameter("quantity");

		Product product = new Product(id, brand, name, quantity);
		try {
			productDao.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("list");

	}
	
	// default
	private void listProduct(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException {
		try {
			List<Product> listProduct = productDao.selectAll();
			request.setAttribute("listProduct", listProduct);
			RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
