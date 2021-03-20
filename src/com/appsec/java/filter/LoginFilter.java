package com.appsec.java.filter;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appsec.java.model.Person;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

	private static HashMap<String, Boolean> AUTHENTICATED_URLS = new HashMap<String, Boolean>();

	public void fillAuthenticatedURLs() {
		AUTHENTICATED_URLS.put("/", false);
		AUTHENTICATED_URLS.put("/changePassword", false);
		AUTHENTICATED_URLS.put("/index.jsp", false);
		AUTHENTICATED_URLS.put("/logout", false);
		AUTHENTICATED_URLS.put("/person", false);
		AUTHENTICATED_URLS.put("/personSearch.jsp", false);
		AUTHENTICATED_URLS.put("/ping.jsp", true);
		AUTHENTICATED_URLS.put("/transferFunds", true);
	}

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession sess = req.getSession(false);

		String path = req.getRequestURI().substring(req.getContextPath().length());

		boolean needAuthentication = false;
		boolean needPrivileges = false;

		for (String page : AUTHENTICATED_URLS.keySet()) {
			if (path.equalsIgnoreCase(page)) {
				needAuthentication = true;
				needPrivileges = AUTHENTICATED_URLS.get(page);
				break;
			}
		}
		
		Person user = null;
		
		if (sess != null && needAuthentication) {
			user = (Person) sess.getAttribute("currentUser");
		}
		
		boolean isLogged = sess != null && user != null;
		boolean isAdmin = user != null && user.getIsAdmin();
			
		if(needAuthentication && !isLogged) {
			res.sendRedirect(req.getContextPath() + "/login");
		}
		
		if (!needAuthentication
				|| (isLogged && !needPrivileges)
				|| (isLogged && needPrivileges && isAdmin)) {
			chain.doFilter(request, response);
		} else if (needPrivileges && !isAdmin) {
			res.getWriter().println("You do not have access to this page");
			res.setStatus(401);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		if (AUTHENTICATED_URLS.isEmpty()) {
			fillAuthenticatedURLs();
		}
	}

}
