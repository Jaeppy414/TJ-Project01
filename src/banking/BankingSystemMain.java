package banking;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

//main 메서드를 포함한 클래스. 프로그램은 여기서 실행한다. 
public class BankingSystemMain {
	
	//메뉴
	public static void showMenu() {
		System.out.println();
		System.out.println(" ------------ Menu -----------");
		System.out.print("[1] 계좌개설  ");
		System.out.print("[2] 입금");
		System.out.println("  [3] 출금");
		System.out.println("[4] 전체계좌정보출력");
		System.out.println("[5] 자동저장선택");
		System.out.println("[6] 프로그램 종료 ");
		System.out.print("메뉴선택>>> ");	
	}
	public static void main(String[] args) {
		ㅉ
		System.out.println("1차 프로젝트(학원)");
		
		AccountManager accountmanager = new AccountManager();
		Scanner scanner = new Scanner(System.in);
		accountmanager.readInfo();
		AutoSaver autoSaver = null;
		
		while(true) {
			//메뉴출력할 거 만들기
			showMenu();
			
			try {
				int choice = scanner.nextInt();
				
				if(choice < 1 || choice > 6) {
					MenuSelectException mse = new MenuSelectException();
					throw mse;
				}
				switch (choice) {
				case ICustomDefine.MAKE: 
					accountmanager.makeAccount();
					break;
				case ICustomDefine.DEPOSIT:
					accountmanager.depositMoney();
					break;
				case ICustomDefine.WITHDRAW:
					accountmanager.withdrawMoney();
					break;
				case ICustomDefine.INQUIRE:
					accountmanager.showAccInfo();
					break;
				//자동저장할 수 있는 메뉴 만들기
				case ICustomDefine.AUTOSAVE:
					//자동저장 없거나 살아있는 게 아니라면 자동저장 실행
					if(autoSaver == null || (!autoSaver.isAlive())){
						autoSaver = new AutoSaver(accountmanager);
					}
					accountmanager.AutoSaver(autoSaver);
					break;
				case ICustomDefine.EXIT:
					System.out.println("<<프로그램 종료>>");
					accountmanager.saveInfo();
					return;
				}//switch 끝
			}
			catch(InputMismatchException e) {
				System.out.println("<<숫자를 입력하세요>>");
				scanner.nextLine();
			}
			catch(MenuSelectException mse){
				System.out.println("<<1~6 사이의 숫자를 입력하세요>>");
			}
		}//while 끝
	}//main 끝
}//class 끝
