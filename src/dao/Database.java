package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class Database implements Data{
	
	private final String driver;
	private final String dbUrl;
	private final String dbUser;
	private final String dbPwd;
	private static String LOAD_SQL = "select top 5 user_name, point from user_point where type_id = 1 order by point desc";
	private static String SAVE_SQL = "insert into user_point(user_name, point, type_id) values(?,?,?)";
	
	public Database(HashMap<String, String> param) {
		this.driver = param.get("driver");
		this.dbUrl = param.get("dbUrl");
		this.dbUser = param.get("dbUser");
		this.dbPwd = param.get("dbPwd");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取数据
	 * @return
	 */
	public List<Player> loadData(){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Player> players = new ArrayList<Player>();
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = conn.prepareStatement(LOAD_SQL);
			rs = stmt.executeQuery();
			while(rs.next()) {
				players.add(new Player(rs.getString(1) ,rs.getInt(2)));
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					if(conn != null) conn.close();
					if(stmt != null) conn.close();
					if(rs != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return players;
	}
	
	/**
	 * 存储数据
	 * @param players
	 */
	public void saveData(Player players) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			stmt = conn.prepareStatement(SAVE_SQL);
			stmt.setObject(1, players.getName());
			stmt.setObject(2, players.getPoint());
			stmt.setObject(3, 1);
			stmt.execute();		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					if(conn != null) conn.close();
					if(stmt != null) conn.close();
					if(rs != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
			}
		}		
	}
}
