package shoping;

import java.sql.Types;

public class DeleteShop extends IConnectImpl{

	public DeleteShop() {
		super("education", "1234");
	}
	@Override
	public void execute() {
		try {
			csmt = con.prepareCall("{call ShopDeleteGoods(?, ?)}");
			csmt.setString(1, scanValue("삭제할 상품의 일련번호"));
			csmt.registerOutParameter(2, Types.NUMERIC);
			csmt.execute();
			
			System.out.println("삭제 프로시저 실행결과");
			System.out.println(csmt.getString(2));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	public static void main(String[] args) {

		new DeleteShop().execute();
	}
}
