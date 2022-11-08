package banking;

public class NormalAccount extends Account{
	//자식에서 확장한 멤버변수 : 기본이자율
	int rate;
	//인수생성자
	public NormalAccount(String AccountID, String Name, int Money, int rate) {
		super(AccountID, Name, Money);
		this.rate = rate;
	}	
	/*
	이자 계산방식  : 잔고 + (잔고 * 기본이자) + 입금액
					Money + (Money * rate/100) + Money
	 */
	@Override
	public void deposit(int deposit) {
		Money = Money + (Money * rate/100) + deposit; 
	}
	@Override
	public void showAccInfo() {
		super.showAccInfo();
		System.out.println("기본이율 : " + rate +"%");
	}
	@Override
	public String toString() {
		return super.toString() + "기본이율 : " + rate + "%";
	}
	
}
