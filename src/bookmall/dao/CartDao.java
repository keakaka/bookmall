package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {
	public Boolean insert(CartVo vo) {
		Boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = "insert into cart values(null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getCount());
			pstmt.setLong(2, vo.getBook_no());
			pstmt.setLong(3, vo.getMember_no());
			
			//4. SQL문 실행
			int count = pstmt.executeUpdate();

			if(count == 1) {
				result = true;
			}
			

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
	
	public List<CartVo> getList (Long member_no){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<CartVo> result = new ArrayList<CartVo>();
		try {
			conn = getConnection();
			
			//3. statement 객체 생성
			String sql = "select c.no, b.title, c.count, (b.price*c.count) cart_price " + 
					" from cart c join book b on (c.book_no = b.no)" + 
					" where c.member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, member_no);
			
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			
			//5. 결과 가져오기
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				Long count = rs.getLong(3);
				Long price = rs.getLong(4);
				
				CartVo vo = new CartVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setCount(count);
				vo.setPrice(price);
				
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
