package delegate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDAOImpl;
import models.User;
import services.UserServices;
import utilities.Utility;

public class UserDelegate implements FrontControllerDelegate {

	private UserServices uServ = new UserServices(new UserDAOImpl());

	private ObjectMapper om = new ObjectMapper();

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
				register(req, resp);
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

	private void logIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User loginUser = null;
		loginUser = om.readValue(req.getInputStream(), User.class);
		String username = loginUser.getUsername();
		String password = loginUser.getPassword();
		User u = uServ.getUserByUserName(username);
		System.out.println("PASSWORD: " + password); 
		if (u != null) {
			if (u.checkPassword(password)) {	 
				u.setPassword_hash(""); 
				u.setPassword("");   
				req.getSession().setAttribute("user", u);
				resp.getWriter().write(om.writeValueAsString(u)); 
			} else {
				resp.setStatus(404);
				Utility.PrintJson(resp, "Incorrect password.");
			}
		} else {
			resp.setStatus(404);
			Utility.PrintJson(resp, "No user found with that username.");
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {
		User newUser = null;
		try {
			newUser = om.readValue(request.getInputStream(), User.class);
			PrintWriter out = response.getWriter();
			if (newUser != null) {
				if (newUser.getUsername() != null && newUser.getPassword() != null) {
					User tempUser = uServ.getUserByUserName(newUser.getUsername());
					if (tempUser != null) {
						Utility.PrintJson(response, "A user with this username already exists.");
					} else {
						Utility util = new Utility();
						String newPasswordHash = util.hashPassword(newUser.getPassword()).get(); 
						newUser.setPassword_hash(newPasswordHash);
						int user_id = uServ.registerUser(newUser);
						if (user_id != 0) {
							response.setStatus(HttpServletResponse.SC_CREATED);
							newUser.setId(user_id); 
							newUser.setPassword_hash(null); 
							newUser.setPassword(null); 
							response.getWriter().write(om.writeValueAsString(newUser));
						} else {
							response.setStatus(400);
							Utility.PrintJson(response, "Invalid fields");
						}
					}
				} else {
					response.setStatus(403);
					Utility.PrintJson(response, "Forbidden"); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void logOut(HttpServletRequest req, HttpServletResponse resp) {
		Utility.PrintJson(resp, "logOut");
	}
}