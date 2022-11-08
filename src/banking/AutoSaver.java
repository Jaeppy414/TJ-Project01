package banking;

public class AutoSaver extends Thread{
	
	AccountManager autosaver;
	
	public AutoSaver(AccountManager autosaver) {
		this.autosaver = autosaver;
	}
	@Override
	public void run() {
		try {
			//5초 마다 txt로 자동저장
			while(true) {
				autosaver.autosaveFile();
				sleep(5000);
				System.out.println("자동 저장중...(5초)");
			}
		} 
		catch (Exception e) {
			System.out.println("자동저장을 종료합니다.");
		}
	}
	
}
