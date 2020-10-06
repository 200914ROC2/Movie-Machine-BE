package services;


import javax.servlet.http.HttpServletRequest;

import dao.UserDAO;
import models.User;

public class UserServices {
	
	private UserDAO userDao;
	
	public UserServices(UserDAO ud) {
		userDao = ud;		
	}
	
	public Integer registerUser(User u) {
		return userDao.registerUser(u);
	}
	
	public User getUserByUserName(String username) {
		return userDao.getUserByUserName(username); 
	} 	
	
	public Integer saveFavorite(Integer uid, Integer mid) {
		return userDao.saveFavorite(uid, mid);
	}
	
	public boolean sessionVerify(HttpServletRequest req) {

		User user = null;
		user = (User) req.getSession().getAttribute("user");
		if (user != null) {			
				return true; 
		}
		return false;
	}
	
}
