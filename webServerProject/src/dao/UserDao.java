package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import dto.User;
import prepare.Component;

@Component("userDao")
public class UserDao {
	
	private DataSource ds;
	public void setDataSoure(DataSource ds) {
		this.ds = ds;
	}
	
	public User login(String id,String pwd) {
		User user = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql = "SELECT * FROM wetalkdb.user WHERE ID='"+id+"' and pwd='"+pwd+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				user = new User().setId(rs.getString(1))
								.setPwd(rs.getString(2))
								.setNikName(rs.getString(3))
								.setPhoto(rs.getString(4));
				sql = "SELECT * FROM wetalkdb.friendlist WHERE ID='"+id+"'";
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					user.setFriendList(rs.getString(2));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		return user;
	}
	public boolean idCheck(String id) {
		boolean result = false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql = "SELECT id FROM wetalkdb.user WHERE ID='"+id+"'";
			rs = stmt.executeQuery(sql);
			result = rs.next();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		return result;
	}
	
	public int insertUser(User user) {
		int result = -1;
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql = "insert into wetalkdb.user (id, pwd, nikname, photo) values ('"
					+ user.getId() + "','" + user.getPwd() + "','" + user.getNikName()+ "','" +user.getPhoto() +"')";
			result = stmt.executeUpdate(sql);
			if(result > 0) {
				sql = "insert into wetalkdb.friendlist (id, friendlist) values ('"
						+user.getId()+ "','')";
				result = stmt.executeUpdate(sql);
			}else {
				result = -11; //user 추가 성공 BUT 친구리스트 추가 실패
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		return result;
	}
	
	public int insertFriendList(User user) {
		int result = -1;
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql = "insert into wetalkdb.friendlist (id, friendlist) values ('"
					+ user.getId() + "','" + user.getFriendList() +"')";
			result = stmt.executeUpdate(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		return result;
	}
//	public int UpdateUser(User user,String changWhat) {
//		int result = 0;
//		Connection conn= null;
//		Statement stmt = null;
//		String sql ;
//
//		try {
//			conn = ds.getConnection();
//			stmt = conn.createStatement();
//			sql =  "UPDATE wetalkdb.user SET train_name='"+train.getName()+"', train_type='"+train.getType()
//			+"', stmt='"+train.getStmt()+"', end_date=null WHERE train_id='"+train.getId()+"'";
//			result = stmt.executeUpdate(sql);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(stmt != null) stmt.close();
//				if(conn != null) conn.close();
//			}catch (Exception e) {}
//		}
//		
//		return result;
//	}
//	
	public User getUserInfo(String id) {
		User user = null;
		Connection conn= null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql = "SELECT * FROM wetalkdb.user WHERE ID='"+id+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				user = new User().setId(rs.getString(1))
						 		 .setNikName(rs.getString(3))
						 		 .setPhoto(rs.getString(4));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		return user;
	}
	public String getFriendList(String id) {
		String friends = null;
		Connection conn= null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql = "SELECT * FROM wetalkdb.friendlist WHERE ID='"+id+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				friends =rs.getString(2);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		return friends;
	}
	public int UpdateFriendList(User user) {
		int result = 0;
		Connection conn= null;
		Statement stmt = null;
		String sql ;

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql =  "UPDATE wetalkdb.friendlist SET friendlist='"+user.getFriendList()+"'  WHERE id='"+user.getId()+"'";
			result = stmt.executeUpdate(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		
		return result;
	}
	public int updateUserInfo(User user, String what) {
		int result = 0;
		Connection conn= null;
		Statement stmt = null;
		String sql = null ;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			if(what.equals("photo")) {
				sql =  "UPDATE wetalkdb.user SET photo='"+user.getPhoto()+"'  WHERE id='"+user.getId()+"'";
			}else if(what.equals("nikName")) {
				sql =  "UPDATE wetalkdb.user SET nikname='"+user.getNikName()+"'  WHERE id='"+user.getId()+"'";
			}
			result = stmt.executeUpdate(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {}
		}
		
		return result;
	}
	
}
