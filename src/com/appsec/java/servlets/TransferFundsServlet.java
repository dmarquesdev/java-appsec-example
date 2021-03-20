package com.appsec.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appsec.java.dao.PersonDAO;
import com.appsec.java.model.Person;

/**
 * Servlet implementation class TransferFundsServlet
 */
@WebServlet("/transferFunds")
public class TransferFundsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersonDAO dao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferFundsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Person> users = dao.list();
		
		request.setAttribute("users", users);
		
		request.getRequestDispatcher("/WEB-INF/transferFunds.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long fromId = Long.parseLong(request.getParameter("from"));
		Long toId = Long.parseLong(request.getParameter("to"));
		Double value = Double.parseDouble(request.getParameter("value"));
		
		Person from = dao.findById(fromId);
		Person to = dao.findById(toId);
		
		// Negative values are not acceptable
		if(value < 0) {
			value = value * -1;
		}
		
		if(from.getFunds() >= value) {
			to.setFunds(to.getFunds() + value);
			from.setFunds(from.getFunds() - value);
			
			dao.save(from);
			dao.save(to);
		}
		
		doGet(request, response);
	}

}
