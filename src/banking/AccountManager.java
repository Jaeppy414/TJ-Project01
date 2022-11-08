package banking;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountManager {
	
	Scanner scanner = new Scanner(System.in);
	HashSet<Account> myAccount;
	DaemonThread dt = new DaemonThread(); //데몬쓰레드
	
	public AccountManager() { 
		myAccount = new HashSet<Account>();
	}

	//계좌개설
	public void makeAccount() {
		
		String AccountID, Name, grade;
		int Money, rate;
		
		System.out.println("신규계좌 개설 선택");
		System.out.println("[1] 보통계좌   [2] 신용계좌");
		int select = scanner.nextInt();
		scanner.nextLine();
		
		System.out.print("계좌번호: "); AccountID = scanner.nextLine();
		System.out.print("이름: "); Name = scanner.nextLine();
		System.out.print("잔고: "); Money = scanner.nextInt();
		scanner.nextLine();	
		System.out.print("기본이자%(정수형태로 입력): "); rate = scanner.nextInt();
		scanner.nextLine();	
		
		
		Account acc = null;
			if(select == 1) {
				//일반계좌
				acc = new NormalAccount(AccountID, Name, Money, rate);
			}
			else if(select == 2) {
				//신용계좌
				System.out.print("신용등급(A, B, C등급): "); grade = scanner.nextLine();
				
				if(grade.equalsIgnoreCase("A") || grade.equalsIgnoreCase("B") || grade.equalsIgnoreCase("C")) {
					acc = new HighCreditAccount(AccountID, Name, Money, rate, grade);
				}
				else {
					System.out.println("신용등급을 잘못 입력했습니다.");
					return;
				}
			}
			
			if (!(myAccount.add(acc))){
				System.out.println("중복된 계좌가 발견되었습니다.");
				System.out.println("계좌를 덮어씌우려면 [Y] / 계좌개설을 중지하려면 [N]");
				String go = scanner.nextLine();
				
				if(go.equalsIgnoreCase("Y")) {
					myAccount.remove(acc);
					myAccount.add(acc);
					System.out.println("계좌를 덮어씌웠습니다.");
				}
				else {
					System.out.println("계좌개설을 종료합니다.");
					return;
				}
			}
			System.out.println("계좌개설이 완료되었습니다.");
		}
	//입금
	public void depositMoney() {
		
		System.out.println("계좌번호와 입금할 금액을 입력하세요.");
		System.out.print("계좌번호:"); String AccountID = scanner.nextLine();
		
		try {
			System.out.print("입금액: "); int depMoney = scanner.nextInt();
			scanner.nextLine();
			
			if(depMoney < 0) { 
				System.out.println("음수는 입력할 수 없습니다.");
				NgtNumberException e = new NgtNumberException();
				throw e;
			}
			if(depMoney%500 != 0) { 
				System.out.println("500원단위로 입력하세요");
			}
			else {
				for(Account act : myAccount) {
					if(AccountID.equals(act.AccountID)) {
					act.deposit(depMoney);	
					}
				}
			}
			System.out.println("입금 완료");
		}
		catch (NgtNumberException e) {
			e.printStackTrace();
		}
		catch (InputMismatchException e) {
			System.out.println("숫자를 입력하세요");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//출금
	public void withdrawMoney() {
		try {
		System.out.println("계좌번호와 출금할 금액을 입력하세요");
		System.out.print("계좌번호:"); String AccountID = scanner.nextLine();
			
			for(Account act : myAccount) {
				if(AccountID.equals(act.AccountID)) {
					System.out.print("출금액:"); int wtMoney = scanner.nextInt();
					scanner.nextLine();
					
					if(wtMoney < 0) {
						System.out.println("음수는 입력할 수 없습니다.");
						NgtNumberException e = new NgtNumberException();
						throw e;
					}
					else if(wtMoney > act.Money) {
						System.out.println("전체 잔액보다 적습니다");
						System.out.println("전체금액을 출금할까요?");
						System.out.println("Yes or No");
						
						String yn = scanner.nextLine();
						if(yn.equalsIgnoreCase("Yes")) {
							act.Money -= act.Money; //이렇게 해야 전체 출금
						}
						else if(yn.equalsIgnoreCase("No")){	
							System.out.println("출금요청취소");
						}
						//잔액비교한 거랑 위치바꿈
					}
					else if(wtMoney%1000 != 0) {
						System.out.println("1000원 단위로 출금할 수 있습니다.");
					}
					else {
						act.Money -= wtMoney;
						System.out.println("출금완료");
						break;
					}
				}
			}
		}
		catch(InputMismatchException e) {
			e.printStackTrace();
		}
		catch(NgtNumberException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//전체정보출력
	public void showAccInfo() {
		for(Account account : myAccount) {
			account.showAccInfo();
		}
		System.out.println("==계좌전체정보가 출력되었습니다==");
	}
	
	//프로그램 종료시 계좌의 정보를 직렬화한다.
	public void saveInfo() {
		try {
			//친구 정보를 파일에 저장하기 위한 출력스트림을 생성한다.
			ObjectOutputStream out =
				new ObjectOutputStream(
					new FileOutputStream("src/banking/AccountInfo.obj")
				);
			for(Account account : myAccount) {
				//파일에 저장한다. 즉 직렬화한다.
				out.writeObject(account);
			}
			out.close();
		} 
		catch (Exception e) {
			System.out.println("계좌정보 직렬화 중 예외발생");
		}
	}
	//프로그램이 시작되면 저장된 파일을 통해 복원 후 컬렉션에 추가
	public void readInfo() {
		try {
			ObjectInputStream in =
				new ObjectInputStream(
					new FileInputStream("src/banking/AccountInfo.obj")
				);
			//파일에 계좌의 정보가 몇개 저장되었는지 확인할 수 없으므로 무한루프로 구성한다.
			
			while(true) {
				//직렬화될 때 Object기반으로 저장되므로 역직렬화할 때는 반드시 다운캐스팅 해야 한다.
				Account account = (Account)in.readObject();
				myAccount.add(account);
				//만약 더이상 복원할 객체가 없다면 예외가 발생한다.
			}
		}
		catch (Exception e) {
			//예외가 발생하면 catch절로 예외객체가 던져지므로 while루프를 탈출할 수 있다.
			System.out.println("더 이상 복원할 객체가 없습니다.");
		}
	}
	//자동저장 메소드
	public void AutoSaver(AutoSaver autoThread) {
		System.out.println("-------- 자동저장 옵션선택 --------");
		System.out.println("1 = 자동저장(On)  0 = 자동저장(Off)");

		int autoselect = scanner.nextInt();
		scanner.nextLine();
		//자동저장 실행
		if(autoselect == 1) {
			//자동저장이 이미 살아있다면
			if(autoThread.isAlive()) {
				System.out.println("자동저장이 이미 실행중입니다.");
			}
			//자동저장이 업었다면
			else {
				//데몬쓰레드로 종속쓰레드로 만들기?
				autoThread.setDaemon(true);
				autoThread.start();
				System.out.println("자동저장을 실행합니다.");
				//자동저장 실행 자동저장 쓰레드는  start()
			}
		}
		//자동저장 정지
		else if(autoselect == 0) {
			//자동저장이 실행중이라면
			if(autoThread.isAlive()) {
				//자동저장 정지 쓰레드는  interrupt()
				System.out.println("자동저장을 정지합니다.");
//				autoThread.setDaemon(true);
				autoThread.interrupt();
			}
			else if(!(autoThread.isAlive())) {
				System.out.println("자동저장이 이미 정지되어 있습니다.");
				return;
			}
			//프로그램 종료시 자동저장 같이 종료되는 데몬쓰레드로 만들어야 함
					
		}
		else if(autoselect != 1 || autoselect != 0){
			System.out.println("1 또는 0 을 선택해야 합니다.");
			return;
		}
	}
	//자동저장될 곳 만들기
	public void autosaveFile() {
		try {
			//1. 파일 저장을 위한 출력스트림 만들기
			//자동저장되는 위치는  AutoSaveAccount.txt
			PrintWriter out = new PrintWriter(new PrintWriter("src/banking/AutoSaveAccount.txt"));
			//2. 확장for문으로 hashset의 저장된 데이터를 account형으로 가져옴
			for(Account acc : myAccount) {
				out.println(acc); 
			}
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}