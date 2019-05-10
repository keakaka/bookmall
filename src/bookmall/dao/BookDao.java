package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.BookVo;

public class BookDao {
	public Boolean insert(BookVo vo) {
		Boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = "insert into book values(null, ?, ?, ?, '재고있음')";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getCate_no());
			pstmt.setString(2, vo.getTitle());
			pstmt.setLong(3, vo.getPrice());
			
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
	
	public List<BookVo> getList (){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<BookVo> result = new ArrayList<BookVo>();
		try {
			conn = getConnection();
			
			//3. statement 객체 생성
			String sql = "select b.no, c.name, b.title, b.price, b.status " + 
					" from book b join category c on(b.cate_no = c.no)";
			pstmt = conn.prepareStatement(sql);
			
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			
			//5. 결과 가져오기
			while(rs.next()) {
				Long no = rs.getLong(1);
				String category = rs.getString(2);
				String title = rs.getString(3);
				Long price = rs.getLong(4);
				String status = rs.getString(5);
				
				BookVo vo = new BookVo();
				vo.setNo(no);
				vo.setCategory(category);
				vo.setTitle(title);
				vo.setPrice(price);
				vo.setStatus(status);
				
				result.add(vo);
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
