package delegate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper; 

 
import utilities.Utility;

 

public class MovieDelegate implements FrontControllerDelegate {

	

	

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = (String) req.getAttribute("path");
		if (path == null || path.equals("")) {
			if ("GET".equals(req.getMethod())) {  
				resp.setContentType("text/html");

			      PrintWriter out = resp.getWriter(); 
			      String title = "Test Using GET Method to Read Form Data"; 
			      String docType =
			         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			         
			      out.println(docType +
			         "<html>\n" +
			            "<head><title>" + title + "</title></head>\n" +
			            "<body bgcolor = \"#f0f0f0\">\n" +
			               "<h1 align = \"center\">" + title + "</h1>\n" +
			               "<ul>\n" +
			                  "  <li><b>First Name</b>: "
			                  +  req.getParameter("username") + "\n" + 
			                  "  <li><b>Last Name</b>: "
			                  +  req.getParameter("password") + "\n" +
			               "</ul>\n" +
			            "</body>" +
			         "</html>"
			      );
			}
			else		
			{
				 
			}
		} else if (path.contains("register")) {
			if ("POST".equals(req.getMethod())) {
				

			} else
				Utility.PrintJson(resp, "register Invalid Credentials");
		} 
		
		/*    LOGOUT     */
		
		else if (path.contains("logout")) {
			if ("POST".equals(req.getMethod()))
				logOut(req, resp);
			else
				Utility.PrintJson(resp, "logout Invalid Credentials");
		} 
		
		/*    LOGIN     */
		
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

	private void getPopular(HttpServletRequest req, HttpServletResponse resp) {
		
	}
	
	private void search(HttpServletRequest req, HttpServletResponse resp) {
		
	}

	private void logIn(HttpServletRequest req, HttpServletResponse resp) { 
		
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {
		

	}
	
	private void logOut(HttpServletRequest req, HttpServletResponse resp) {
		
	}
}
