package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample1 {
	
	// step1 : JDBC Driver에 필요한 필수연결정보
	private static String jdbcUrl = "jdbc:oracle:thin:@db20220510181503_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	private static String driverClass = "oracle.jdbc.OracleDriver";
	private static String user = "HR";
	private static String pass = "Oracle12345678";
	

	public static void main(String[] args) { 
//			throws ClassNotFoundException, SQLException { // 예외처리 try-catch로 해보자
		log.trace("main({}) invoked", Arrays.toString(args));
		
		// step2 : JDBC를 위한 지역변수 선언
		Connection conn = null;		
		Statement stmt = null;  // java.sql로 임포트
		ResultSet rs = null;
		
		try {
			// step4 : JDBC Driver Class를 등록 (registration)
			Class.forName(driverClass); // 예외처리 해주자
			
			// step5 : 
			conn = DriverManager.getConnection(jdbcUrl, user, pass); // 예외처리 해주자
			log.info("\t+ conn: {}", conn);
			
			// step6 : Connection 객체로부터 Statement 객체를 생성할 수 있다
			stmt = conn.createStatement();
			
			// step7 : 실제 수행시킬 SQL 문장 생성
			String sql = "Select current_date From dual";  // DQL 문장
			
			// step7 : Statement 실행 (SQL 문장을 전송)
			rs = stmt.executeQuery(sql);			// if DQL (SELECT 문장에만 해당)
			
			// step8 : ResultSet을 이용해서 각 레코드 별로 각 컬럼의 값을 추출
			if(rs.next()) {
				// 현재 레코드의 구성 컬럼의 값을 추출
	            // rs.get<추출컬럼타입에 대응되는 자바타입>("컬럼명");
				Timestamp now = rs.getTimestamp("current_date");  // java.sql로 임포트
				log.info("\t+ now: {}", now);
			};
		
		} catch(SQLException | ClassNotFoundException e) { // 멀티 캐치		
			e.printStackTrace();		
		}  finally {
			// step9 : 자원객체는 썼으면 해제해주자 (순서중요!)
			// 순서 : ResultSet -> Statement -> Connection
			try { if (rs != null && !rs.isClosed()) rs.close();	} catch(SQLException e) {;;} // try-catch
			try { if (stmt != null && !stmt.isClosed()) stmt.close(); } catch(SQLException e) {;;} // try-catch
			try { if (conn != null && !conn.isClosed()) conn.close(); } catch(SQLException e) {;;} // try-catch
		} // try-catch-finally
		
		System.exit(0);
	} // main

} // end class
