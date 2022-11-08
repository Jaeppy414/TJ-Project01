package banking;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

public abstract class Account implements Serializable {
	//멤버변수 
	String AccountID; //계좌번호
	String Name; //고객이름
	int Money; //잔고
	
	public Account(HashSet<Account> accHashSet) {
		
	}
	//인수생성자 : 멤버변수 초기화
	public Account(String AccountID,String Name,int Money) {
		this.AccountID = AccountID;
		this.Name = Name;
		this.Money = Money;
	}
	//멤버변수 출력을 위한 멤버메서드
	public void showAccInfo() {
		System.out.println("==========계좌정보==========");
		System.out.print("계좌번호: "+ AccountID);
		System.out.print(",   이름: "+ Name);
		System.out.print(",   잔고: "+ Money);
		System.out.println();
	}	
	public void deposit(int deposit) {
		//오버라이딩하기 위한 메서드
	}
	@Override
	public int hashCode() {
		int returnCode = this.AccountID.hashCode();
		return returnCode;
	}
	@Override
	public boolean equals(Object obj) {
		Account account = (Account)obj;
		if(account.AccountID.equals(this.AccountID)) {
			return true;
		}
		else
			return false;
	}
	@Override
	public String toString() {
		return "계좌번호 : " + AccountID + " 이름 : " + Name + " 잔고 : "+ Money;
	}
}