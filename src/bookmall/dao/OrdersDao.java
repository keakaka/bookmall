package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bookmall.vo.BookVo;
import bookmall.vo.OrdersVo;

public class OrdersDao {
	public Boolean insert(Long member_no, int num, String deli_add) {
		Boolean result = false;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
	
		try {
			conn = getConnection();
			String sql = "select c.count, c.book_no, b.price from cart c join book b on(c.book_no = b.no) where c.no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);
			
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			Long count = 0L;
			Long book_no = 0L;
			OrdersVo vo = new OrdersVo();
			if(rs.next()) {
				count = rs.getLong(1);
				book_no = rs.getLong(2);
				vo.setPay_price(rs.getLong(1)*rs.getLong(3));
				vo.setMember_no(member_no);
				vo.setDeli_add(deli_add);
			}
			
			sql = "select count(*) from orders where date(pay_date) = date(now())";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Long sequence = 0L;
			if(rs.next()) {
				sequence = rs.getLong(1);
			}
			sequence++;
			
			sql = "insert into orders values(null, ?, ?, ?, now(), concat(date_format(now(), '%Y%m%d'), '-', LPAD(?, 5, '0')))";
			pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getMember_no());
			pstmt.setString(2, vo.getDeli_add());
			pstmt.setLong(3, vo.getPay_price());
			pstmt.setLong(4, sequence);
			int check1 = pstmt.executeUpdate();
			
			if(check1 > 0) {
				stmt = conn.createStatement();
				sql = "select last_insert_id()";
				rs = stmt.executeQuery(sql);
				Long order_no = 0L;
				if(rs.next()) {
					order_no = rs.getLong(1);
				}
				sql = "insert into orders_book values(null, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, count);
				pstmt.setLong(2, order_no);
				pstmt.setLong(3, book_no);
				//4. SQL문 실행
				int check = pstmt.executeUpdate();

				result = check == 1;
			}
			

		} catch (SQLException e) {
			System.out.println("error " + e);
			e.printStackTrace();
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
	
	public Boolean insert(List<OrdersVo> list) {
		Boolean result = false;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
	
		try {
			Long price = 0L;
			String sql = "";
			conn = getConnection();
			for(OrdersVo vo : list) {
				sql = "select price from book where no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, vo.getBook_no());
				
				//4. SQL문 실행
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					price += (rs.getLong(1)*vo.getCount());
				}
			}
			
			sql = "select count(*) from orders where date(pay_date) = date(now())";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Long sequence = 0L;
			if(rs.next()) {
				sequence = rs.getLong(1);
			}
			sequence++;
			
			sql = "insert into orders values(null, ?, ?, ?, now(), concat(date_format(now(), '%Y%m%d'), '-', LPAD(?, 5, '0')))";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, list.get(0).getMember_no());
			pstmt.setString(2, list.get(0).getDeli_add());
			pstmt.setLong(3, price);
			pstmt.setLong(4, sequence);
			
			int check1 = pstmt.executeUpdate();
			
			if(check1 > 0) {
				stmt = conn.createStatement();
				sql = "select last_insert_id()";
				rs = stmt.executeQuery(sql);
				Long order_no = 0L;
				if(rs.next()) {
					order_no = rs.getLong(1);
				}
				sql = "insert into orders_book values(null, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				for(OrdersVo vo : list) {
					pstmt.setLong(1, vo.getCount());
					pstmt.setLong(2, order_no);
					pstmt.setLong(3, vo.getBook_no());
					//4. SQL문 실행
					int check = pstmt.executeUpdate();
					if(check != 1) {
						break;
					}
				}
				result = true;
			}
			

		} catch (SQLException e) {
			System.out.println("error " + e);
			e.printStackTrace();
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
	
	public List<OrdersVo> getOrdersList (Long member_no){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<OrdersVo> result = new ArrayList<OrdersVo>();
		try {
			conn = getConnection();
			
			//3. statement 객체 생성
			String sql = "select pay_no, pay_price, pay_date, deli_add from orders where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, member_no);
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			
			//5. 결과 가져오기
			while(rs.next()) {
				String pay_no = rs.getString(1);
				Long pay_price = rs.getLong(2);
				String pay_date = rs.getString(3);
				String deli_add = rs.getString(4);
				
				OrdersVo vo = new OrdersVo();
				vo.setPay_no(pay_no);
				vo.setPay_price(pay_price);
				vo.setPay_date(pay_date);
				vo.setDeli_add(deli_add);
				result.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<OrdersVo> getOrdersBookList(Long member_no, String pay_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<OrdersVo> result = new ArrayList<OrdersVo>();
		try {
			conn = getConnection();
			
			//3. statement 객체 생성
			String sql = "select no from orders where member_no = ? and pay_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, member_no);
			pstmt.setString(2, pay_no);
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			Long order_no = 0L;
			if(rs.next()) {
				order_no = rs.getLong(1);
			}else {
				return null;
			}
			
			sql = "select b.title, ob.count from orders_book ob join book b on (ob.book_no = b.no) where orders_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, order_no);
			rs = pstmt.executeQuery();
			//5. 결과 가져오기
			while(rs.next()) {
				String title = rs.getString(1);
				Long count = rs.getLong(2);
				
				OrdersVo vo = new OrdersVo();
				vo.setTitle(title);
				vo.setCount(count);
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
