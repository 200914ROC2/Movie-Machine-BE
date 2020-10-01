package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class LoginController extends HttpServlet{
	 
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 
		 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	//	process(request,response);
		
		 // Set response content type
	      response.setContentType("text/html");

	      PrintWriter out = response.getWriter(); 
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
	                  + request.getParameter("username") + "\n" +
	                  "  <li><b>Last Name</b>: "
	                  + request.getParameter("password") + "\n" +
	               "</ul>\n" +
	            "</body>" +
	         "</html>"
	      );
	}
}
