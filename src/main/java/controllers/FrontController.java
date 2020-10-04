package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import delegate.FrontControllerDelegate;
import utilities.Utility;



/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	
	private RequestHandler rh = new RequestHandler();	 
	
	// this for all requests
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		FrontControllerDelegate fcd = rh.handle(request, response);
		
		if(fcd != null)
			fcd.process(request, response); 
		else
			response.sendError(HttpServletResponse.SC_NOT_FOUND); 
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//JSONObject obj = new JSONObject();	  	
	//	obj.put("message", "test"); 
		//Utility.PrintJson(response, "GET TEST");   
		//System.out.println("--------------TEST-------------------------"); 
		process(request,response);  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		process(request,response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	} 

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	

}
