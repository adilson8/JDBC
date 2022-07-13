package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample5 {
	
	// step1 : JDBC Driver에 필요한 필수연결정보
	private static String jdbcUrl = "jdbc:oracle:thin:@db20220510181503_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	private static String user = "HR";
	private static String pass = "Oracle12345678";
	
	public static void main(String[] args) { 
		log.trace("main({}) invoked", Arrays.toString(args));
		
		try {	
			@Cleanup Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);

			// 1. Dynamic SQL (동적인 SQL) => Database에 상당한 부담을 준다.
			@Cleanup Statement stmt = conn.createStatement(); // Dynamic SQL에 대한 문장생성
			
			// 2. Prepared SQL (정적인 SQL) => Database에 최적화된 처리를 가능하게 해줌
			String sql = "DELETE FROM TBL_EMP where EMPLOYEE_ID = ?"; // ? : Bind Variable 
			@Cleanup PreparedStatement pstmt= conn.prepareStatement(sql);
			
			// 3. Binding Variable에 값을 설정해주기 (Binding)
			pstmt.setInt(1, 167);
			int affectedLines = pstmt.executeUpdate();
			log.info("\t+ affectedLines : {}", affectedLines);			
			
		} catch(SQLException e) { 	
			e.printStackTrace();					
		} // try-catch
	} // main

} // end class
