package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;

public class CategoryDao {
	public Boolean insert(CategoryVo vo) {
		Boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = "insert into category values(null, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			
			//4. SQL문 실행
			int count = pstmt.executeUpdate();

			result = count == 1;


		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<String> getList (){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<String> result = new ArrayList<String>();
		try {
			conn = getConnection();
			
			//3. statement 객체 생성
			String sql = "select name from category";
			pstmt = conn.prepareStatement(sql);
			
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			
			//5. 결과 가져오기
			while(rs.next()) {
				String name = rs.getString(1);
				
				result.add(name);
			}
			
		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			//1. JDBC Driver(MariaDB) 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mariadb://192.168.1.236:3307/bookmall";
			conn = DriverManager.getConnection(url, "bookmall", "bookmall");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}
		
		return conn;
	}
}
