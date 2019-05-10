package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.MemberVo;


public class MemberDao {
	public Boolean insert(MemberVo vo) {
		Boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = "insert into member values(null, ?, ?, ?, ?, ?, 1)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getPhone());
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
	
	public List<MemberVo> getList (){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		List<MemberVo> result = new ArrayList<MemberVo>();
		try {
			conn = getConnection();
			
			//3. statement 객체 생성
			String sql = "select no, id, name, email, phone from member";
			pstmt = conn.prepareStatement(sql);
			
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			
			//5. 결과 가져오기
			while(rs.next()) {
				Long no = rs.getLong(1);
				String id = rs.getString(2);
				String name = rs.getString(3);
				String email = rs.getString(4);
				String phone = rs.getString(5);
				
				MemberVo vo = new MemberVo();
				vo.setNo(no);
				vo.setId(id);
				vo.setName(name);
				vo.setEmail(email);
				vo.setPhone(phone);
				
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
	
	public Long login(Long no) {
		Long result = 0L;
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			//3. statement 객체 생성
			String sql = " select no from member where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			//4. SQL문 실행
			rs = pstmt.executeQuery();
			
			//5. 결과 가져오기
			
			if(rs.next()) {
				result = rs.getLong(1);
			}else {
				return -1L;
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
