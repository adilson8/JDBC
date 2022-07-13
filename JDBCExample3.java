package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample3 {
	
	// step1 : JDBC Driver에 필요한 필수연결정보
	private static String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@db20220510181503_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	private static String user = "HR";
	private static String pass = "Oracle12345678";
	
	public static void main(String[] args) { 
		log.trace("main({}) invoked", Arrays.toString(args));
		
		try {	
			@Cleanup Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			@Cleanup Statement stmt = conn.createStatement();
			
			String sql = "Select current_date, sysdate From dual"; 
			@Cleanup ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				Timestamp now = rs.getTimestamp("current_date");
				log.info("\t+ now: {}", now);
				rs.getString("sysdate");
			};			
		
		} catch(SQLException e) { 	
			e.printStackTrace();					
		} // try-catch
	} // main

} // end class
