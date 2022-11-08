package shoping;

import java.sql.SQLException;

public class SelectShop extends IConnectImpl{

	public SelectShop() {
		super("education", "1234");
	}

	@Override
	public void execute() {
		try {
			
			while(true) {
				String scan =scanValue("찾는이름:");
				stmt = con.createStatement();
				
				String sql = "SELECT g_idx, goods_name,"
						+ " trim(to_char(goods_price,'999,999,999')), "
						+ " to_char(regidate, 'yyyy-mm-dd hh24:mi'), p_code FROM sh_goods "
						+ " WHERE goods_name like '%"+ scan +"%'";
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					String idx = rs.getString(1);
					String g_name = rs.getString(2);
					String g_price = rs.getString(3);
					String regidate = rs.getString(4);
					String p_code = rs.getString(5);
					
					System.out.printf("일련번호 : %s\n제품명 : %s   가격 : %s\n생산일자 : %s\n제품코드 : %s\n",
							idx, g_name, g_price, regidate, p_code);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	public static void main(String[] args) {
		new SelectShop().execute();
	}	
}
