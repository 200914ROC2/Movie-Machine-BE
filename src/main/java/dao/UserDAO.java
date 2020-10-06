package dao;

import models.User;
import java.util.Set;

public interface UserDAO {
	public Integer registerUser(User u);
	public User getUserById(Integer id);
	public User getUserByUserName(String username); 
	public User getUserByUserNameAndPassword(String username, String password);
	public Integer saveFavorite(Integer uid, Integer mid);
	public void updateUser(User u);
	public void deleteUser(User u);
	public Set<Integer> getFavoritesByUserId(Integer uid);
}
