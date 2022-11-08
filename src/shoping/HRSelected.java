package shoping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HRSelected {

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "education";
			String pass = "1234";
			
			Connection con = DriverManager.getConnection(url, id, pass);
			if(con!=null) {
				System.out.println("Oracle 연결성공");
				/*
				1. 쿼리문 작성
				: 작성시 줄바꿈을 할 때 앞뒤로 스페이스를 삽입하는 것이 좋다.
				그렇지 않으면 문장이 서로 이어지게 되어 SyntaxError가 발생하게 된다.
				 */
				String sql = "SELECT * FROM sh_goods WHERE "
						+ " p_code = 1 "
						+ " ORDER BY p_code DESC";
				/*
				2. 쿼리문 전송을 위해 Statement인터페이스를 통해 객체를 생성한다.
				 */
				Statement stmt = con.createStatement();
				/*
				3. 쿼리문을 오라클 데이터베이스로 전송한다. 
				실행한 결과는 ResultSet객체를 통해 반환받는다.
				 */
				ResultSet rs = stmt.executeQuery(sql);
				/*
				4. 반환된 결과를 갯수만큼 반복하여 출력한다. next()메서드는 
				출력할 레코드가 남았는지 확인하고, 
				더 이상 레코드가 남아있지 않다면 false를 반환하여 while문을 탈출한다.
				 */
				while(rs.next()) {
					/*
					5. getXXXX() 메서드를 통해 각 컬럼에 접근한다. 오라클의 자료형은 
					문자 ,숫자, 날짜 3가지 형태이므로 
					메서드도 이와 동일한 형태를 가지고 있다. 각 컬럼에 접근시 
					인덱스와 컬럼명 두가지를 사용할 수 있다.
					 */
					String goods_name = rs.getString("goods_name");
					int goods_price = rs.getInt("goods_price");
					java.sql.Date regidate = rs.getDate("regidate");
					int p_code = rs.getInt("p_code");
					System.out.printf("%s %d %s %d",
							goods_name, goods_price, regidate, p_code);
					System.out.println();
				}
				/*
				6. 자원반납(해제) : 모든 작업을 마친 후에는 메모리 절약을 위해 
				연결했던 자원을 반납한다.
				 */
				rs.close();
				stmt.close();
				con.close();
			}
			else {
				System.out.println("Oracle 연결실패");
			}
		} catch (Exception e) {
			System.out.println("Oracle 연결시 예외발생");
			e.printStackTrace();
		}
	}

}
