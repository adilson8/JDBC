package org.zerock.myapp;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCExample7 {
	
	// 우리가 직접 자원객체를 만들고 try-with-resources  블록에 적용해서
	// 과연 자원객체를 어떤 식으로 차례대로 닫는지 경험해봅시다
	public static void main(String[] args) {
		Resource1 res1 = new Resource1();
		Resource2 res2 = new Resource2();
		Resource3 res3 = new Resource3();
		
		try (res1; res2; res3) { // res3 -> res2 -> res1 순서로 닫는다 (콘솔확인)
			;;
		} catch (Exception e){
			e.printStackTrace();
		} // try-with-resources
	
	} // main

} // end class

@Log4j2
@NoArgsConstructor
class Resource1 implements AutoCloseable {

	@Override
	public void close() throws Exception {
		log.trace("close() invoked.");
	} // close
	
} // end class


@Log4j2
@NoArgsConstructor
class Resource2 implements AutoCloseable { 

	@Override
	public void close() throws Exception {
		log.trace("close() invoked.");
	} // close
	
} // end class


@Log4j2
@NoArgsConstructor
class Resource3 implements AutoCloseable {

	@Override
	public void close() throws Exception {
		log.trace("close() invoked.");
	} // close
	
} // end class
