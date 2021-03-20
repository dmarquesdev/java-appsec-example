package com.appsec.java.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.appsec.java.dao.PersonDAO;
import com.appsec.java.model.Person;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersonDAO dao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Person> users = dao.list();
		request.setAttribute("users", users);
		
		boolean admin = ((Person) request.getSession().getAttribute("currentUser")).getIsAdmin();
		request.setAttribute("admin", admin);
		
		request.getRequestDispatcher("/WEB-INF/changePassword.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String username = request.getParameter("username");
		
		Person user = (Person) request.getSession(false).getAttribute("currentUser");
		
		MessageDigest md5;
		
		if(username != null && !username.equals("")) {
			user = dao.findByUsername(username);
		} else if(oldPassword != null && !oldPassword.equals("")) {
			String oldMD5 = "";
			
			try {
				md5 = MessageDigest.getInstance("MD5");
				md5.update(oldPassword.getBytes());
				oldMD5 = DatatypeConverter.printHexBinary(md5.digest()).toLowerCase();
	
				if(!oldMD5.equals(user.getPassword())) {
					request.setAttribute("message", "Invalid password!");
					newPassword = oldPassword;
				}
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(newPassword.getBytes());
			newPassword = DatatypeConverter.printHexBinary(md5.digest()).toLowerCase();
			user.setPassword(newPassword);
			dao.save(user);
			
			if(request.getAttribute("message") == null && (username != null && !username.equals(""))) {
				request.setAttribute("message", "Password changed!");
			} else {
				request.getSession().setAttribute("currentUser", null);
			}
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
