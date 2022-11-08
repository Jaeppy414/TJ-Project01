package banking;

public class HighCreditAccount extends Account implements ICustomDefine{

	int rate;
	String grade;
	
	public HighCreditAccount(String AccountID, String Name, int Money, int rate, String grade) {
		super(AccountID, Name, Money);
		this.rate = rate;
		this.grade = grade;
	}
	@Override
	public void deposit(int deposit) {
		/*
		신용계좌 : 잔고 + (잔고 * 기본이자) + (잔고 * 추가이자) + 입금액
			Money = Money + ( Money * rate/100) + (Money * grade/100) + Money
		 */
		if(grade.equalsIgnoreCase("A")) {
			Money = Money + (Money * rate/100) + (Money * A/100) + deposit;
		}
		else if(grade.equalsIgnoreCase("B")) {
			Money = Money + (Money * rate/100) + (Money * B/100) + deposit;
		}
		else if(grade.equalsIgnoreCase("C")) {
			Money = Money + (Money * rate/100) + (Money * C/100) + deposit;
		}
	}
	@Override
	public void showAccInfo() {
		super.showAccInfo();
		System.out.println("기본이율 : " + rate +"%");
		System.out.println("신용등급 : " + grade);
	}
	@Override
	public String toString() {
		return super.toString() + "기본이율 : " + rate + "%  신용등급 : " + grade;
	}

}
