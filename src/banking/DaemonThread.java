package banking;

public class DaemonThread extends Thread {

	@Override
	public void run() {
		while(true) {
			try {
				sleep(5000);
				System.out.println("5초마다 자동저장");
			} 
			catch (Exception e) {
				System.out.println("자동저장시 오류발생");
			}
		}
	}
}
