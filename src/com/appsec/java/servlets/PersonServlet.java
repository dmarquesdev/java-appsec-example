package com.appsec.java.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appsec.java.dao.PersonDAO;
import com.appsec.java.exception.DAOException;
import com.appsec.java.model.Person;

/**
 * Servlet implementation class PersonServlet
 */
@WebServlet("/person")
public class PersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersonDAO dao;
	
    public PersonServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String orderBy = request.getParameter("orderBy");
		
		HttpSession session = request.getSession(false);
		Person user = (Person) session.getAttribute("currentUser");
		
		if(name != null && !name.equals("")) {
			if(orderBy == null || orderBy.equals("")) {
				orderBy = "username";
			}
			List<Person> list;
			try {
				list = dao.listByName(name, orderBy);
				for(Person p : list) {
					out.println("<p><b>Email:</b> " + p.getEmail() + "</p>");
					out.println("<p><b>Username:</b> " + p.getUsername() + "</p>");
					out.println("<p><b>Name:</b> " + p.getName() + "</p>");
					
					if(user.getIsAdmin()) {
						out.println("<p><b>Admin:</b> " + p.getIsAdmin() + "</p>");
						out.println("<p><b>Funds:</b> " + p.getFunds() + "</p>");
					}
					
					out.println("<br />");
				}
			} catch (DAOException e) {
				e.printStackTrace(out);
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
