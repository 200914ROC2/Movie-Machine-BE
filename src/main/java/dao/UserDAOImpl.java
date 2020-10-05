package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import models.User;
import utilities.ConnectionUtil;

public class UserDAOImpl implements UserDAO {
	private Connection conn = null;
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil(); // Our connection to the database
	private PreparedStatement stmt = null; // We use prepared statements to help protect against SQL injection

	@Override
	public Integer registerUser(User u) {
		Integer id = 0;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "insert into users values (default, ?, ?, ?, ?)";
			String[] keys = {"user_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPasswordHash());  
			pstmt.setString(3, u.getFirstname()); 
			pstmt.setString(4, u.getLastname());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}

	@Override
	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUserNameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUserByUserName(String username) {
		User user = null;
		try {
			conn = cu.getConnection();
			user = SetUserFromResultSet(conn, "username", username, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
	
	static User SetUserFromResultSet(Connection conn, String rowName, String strValue, int intValue) {

		User user = null;
		try {
			String sql = "select main.uid, main.username, main.firstname, main.lastname,main.password, movie_id from \r\n" + 
					"	(select\r\n" + 
					"		u.user_id as uid, u.username as username, u.firstname as firstname, u.lastname  as lastname,u.password_hash  as password, f.movie_id as movie_id\r\n" + 
					"		from users u\r\n" + 
					"		left join favorite f\r\n" + 
					"		on u.user_id = f.user_id \r\n" + 
					"	) as main where main." + rowName
					+ " = ? order by main.uid";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			switch (rowName) {		
			case "username":
				pstmt.setString(1, strValue); 
				break;
			case "uid":
				pstmt.setInt(1, intValue);
				break;
			default:
				// code block
			}

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setFirstname(rs.getString("firstname"));
				user.setLastname(rs.getString("lastname"));			
				user.setPassword_hash(rs.getString("password")); 

//				Set<Favorite> setFavorites = new HashSet<Favorite>(); 
//				ResultSet rs2 = pstmt.executeQuery();
//
//				while (rs2.next()) {
// 
//					Favorite favorite = new Favorite();
//					favorite.setAccount_id(rs2.getInt("favorite_id"));
//									
//				}
//				user.setFavorites(setFavorites);
				
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return user;

	}
	

}
