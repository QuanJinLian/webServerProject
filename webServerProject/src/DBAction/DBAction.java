package DBAction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DBAction {       // 아이디 중복확인 할때 씀
	private static DBAction instance ;
	private Connection conn;
	
	public DBAction() {

		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/wetalkdb?characterEncoding=UTF-8&serverTimezone=UTC";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","java");
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	}
	public static DBAction getInstance() {
		if(instance == null) {
			instance = new DBAction();
		}
		return instance;
	}
	public Connection getConnection() {
		return conn;
	}
	public void close() {
		try {
			if(conn != null) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
