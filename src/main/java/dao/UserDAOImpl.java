package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
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
	public Integer saveFavorite(Integer uid, Integer mid) {
		Integer id = null;
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "insert into favorite values (?, ?)";
			String[] keys = {"user_id", "movie_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			
			pstmt.setInt(1, uid);
			pstmt.setInt(2, mid);
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			System.out.println(rs);
			if (rs.next()) {
				id = rs.getInt("movie_id");
				System.out.println(rs.getInt("movie_id"));
				conn.commit();
			} else {
				System.out.println("Rolling back commit");
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
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
				user.setPasswordHash(rs.getString("password"));   
				
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return user;

	}

	@Override
	public Set<Integer> getFavoritesByUserId(Integer uid) {
		Set<Integer> userFavorites = null;
		
		try(Connection conn = cu.getConnection()) {
			String sql = "select movie_id from favorite where user_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uid);
			
			ResultSet rs = pstmt.executeQuery();
			
			userFavorites = new HashSet<>();
			
			while (rs.next()) {
				userFavorites.add(rs.getInt("movie_id"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userFavorites;
	}
	

}
