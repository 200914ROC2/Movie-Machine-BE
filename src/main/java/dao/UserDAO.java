package dao;

import models.User;

public interface UserDAO {
	public Integer registerUser(User u);
	public User getUserById(Integer id);
	public User getUserByUserNameAndPassword(String username, String password);
	public void updateUser(User u);
	public void deleteUser(User u);
}
