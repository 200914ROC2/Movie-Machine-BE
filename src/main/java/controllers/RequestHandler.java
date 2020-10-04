package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 


import delegate.FrontControllerDelegate; 
import delegate.UserDelegate;


public class RequestHandler {
	private Map<String, FrontControllerDelegate> delegateMap;

	{
		delegateMap = new HashMap<String, FrontControllerDelegate>();		
		delegateMap.put("user", new UserDelegate()); 	 	 
	}

	public FrontControllerDelegate handle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if ("OPTIONS".equals(req.getMethod())) {
			return new FrontControllerDelegate() {
				public void process(HttpServletRequest r1, HttpServletResponse r2)
						throws ServletException, IOException {   
						}
			};  
		}
		StringBuilder uri_sb = new StringBuilder(req.getRequestURI());
	
		uri_sb.replace(0, req.getContextPath().length() + 1, "");
		
		if (uri_sb.indexOf("/") != -1) {
			req.setAttribute("path", uri_sb.substring(uri_sb.indexOf("/") + 1));
			uri_sb.replace(uri_sb.indexOf("/"), uri_sb.length(), "");
		}
		 
		return delegateMap.get(uri_sb.toString());

	}

	
}
