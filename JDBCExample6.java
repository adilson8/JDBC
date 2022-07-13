package org.zerock.myapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample6 {
	
	// step1 : JDBC Driver에 필요한 필수연결정보
	private static String jdbcUrl = "jdbc:oracle:thin:@db20220510181503_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	private static String user = "HR";
	private static String pass = "Oracle12345678";
	
	public static void main(String[] args) { 
		log.trace("main({}) invoked", Arrays.toString(args));
		
		try {				
			@Cleanup Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			// 2. Prepared SQL (정적인 SQL) => Database에 최적화된 처리를 가능하게 해줌
//			String sql = "SELECT * FROM EMPLOYEES WHERE SALARY > = 7000 AND DEPARTMENT_ID >= 50"; // ? : Bind Variable
			String sql = "SELECT * FROM EMPLOYEES WHERE SALARY > = ? AND DEPARTMENT_ID >= ?";
			@Cleanup PreparedStatement pstmt= conn.prepareStatement(sql);
			
			// 3. Binding Variable에 값을 설정해주기 (Binding)
			pstmt.setDouble(1, 7000);
			pstmt.setDouble(2, 50);
			ResultSet rs = pstmt.executeQuery();
			
			// 4. 레코드별로 모든 컬럼값을 추출하여 콘솔에 출력 
			while(rs.next()) {
				Integer EMPLOYEE_ID 	= rs.getInt("EMPLOYEE_ID");
				String 	FIRST_NAME 		= rs.getString("FIRST_NAME");
				String 	LAST_NAME 		= rs.getString("LAST_NAME");
				String 	EMAIL 			= rs.getString("EMAIL");
				String 	PHONE_NUMBER 	= rs.getString("PHONE_NUMBER");
				Date 	HIRE_DATE 		= rs.getDate("HIRE_DATE");
				String 	JOB_ID 			= rs.getString("JOB_ID");
				Double 	SALARY 			= rs.getDouble("SALARY");
				Double 	COMMISSION_PCT 	= rs.getDouble("COMMISSION_PCT");
				Integer MANAGER_ID 		= rs.getInt("MANAGER_ID");
				Integer DEPARTMENT_ID 	= rs.getInt("DEPARTMENT_ID");
				
				String employee = 
					String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
								EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER,
								HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID,
								DEPARTMENT_ID);
				
				log.info("Employee: {}", employee);
			} // while
			
		} catch(SQLException e) { 	
			e.printStackTrace();					
		} // try-catch
	} // main

} // end class
