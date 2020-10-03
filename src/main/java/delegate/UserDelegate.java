package delegate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilities.Utility;

public class UserDelegate implements FrontControllerDelegate {

 
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		if (path == null || path.equals("")) {
			if ("GET".equals(req.getMethod())) { 
				Utility.PrintJson(resp, "Method Not Allowed");	 				
			} else {
				Utility.PrintJson(resp, "Method Not Allowed");
			}			
		} 
		
		else if (path.contains("favorite")) {
			if ("POST".equals(req.getMethod())) {
				getFavorite(req, resp);
			} else
				Utility.PrintJson(resp, "register Invalid Credentials");
		}
		
		else if (path.contains("register")) {
			if ("POST".equals(req.getMethod())) {

			} else
				Utility.PrintJson(resp, "register Invalid Credentials");
		}

		/* LOGOUT */

		else if (path.contains("logout")) {
			if ("POST".equals(req.getMethod()))
				logOut(req, resp);
			else
				Utility.PrintJson(resp, "logout Invalid Credentials");
		}

		/* LOGIN */

		else if (path.contains("login")) {
			if ("POST".equals(req.getMethod()) && path.contains("login"))
				logIn(req, resp);
			else if ("DELETE".equals(req.getMethod()))
				req.getSession().invalidate();
			else
				Utility.PrintJson(resp, "LOGIN Invalid Credentials");
		} else {
			requestWithId(path, req, resp);
		}
	}

	

	private void requestWithId(String path, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int userId = Integer.valueOf(path);

		switch (req.getMethod()) {
		case "GET":
			Utility.PrintJson(resp, "id: " + userId);

			break;
		case "PUT":

			break;
		case "DELETE":

			break;
		default:

			break;
		}
	} 

	private void getFavorite(HttpServletRequest req, HttpServletResponse resp) {
		Utility.PrintJson(resp, "Get Favorite");
		
	}	

	private void logIn(HttpServletRequest req, HttpServletResponse resp) {
		Utility.PrintJson(resp, "logIn");
	}

	private void register(HttpServletRequest request, HttpServletResponse resp) {
		Utility.PrintJson(resp, "register");
	}

	private void logOut(HttpServletRequest req, HttpServletResponse resp) {
		Utility.PrintJson(resp, "logOut");
	} 
}