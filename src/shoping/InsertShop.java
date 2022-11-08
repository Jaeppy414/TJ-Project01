package shoping;

import java.sql.Date;
import java.util.Scanner;

public class InsertShop extends IConnectImpl{
	//생성자 : 부모클래스의 생성자를 호출하여 연결한다.
	public InsertShop() {
		super("education","1234");
	}
	
	//
	@Override
	public void execute() {
		try {
			//1. 쿼리문 준비 : 값의 세팅이 필요한 부분을 ?(인파라미터)로 기술한다.
			String query = "INSERT INTO sh_goods VALUES "
					+ " (seq_total_idx.nextval, ?, ?, SYSDATE, ?)";
			
			//2. prepared객체 생성 : 객체 생성시 준비한 쿼리문을 인수로 전달한다.
			psmt = con.prepareStatement(query);
			
			psmt.setString(1, scanValue("상품명"));
			psmt.setString(2, scanValue("가격"));
			psmt.setString(3, scanValue("코드번호"));
			
			int affected = psmt.executeUpdate();
			System.out.println(affected +"행이 입력되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	public static void main(String[] args) {
		new InsertShop().execute();
	}

}
