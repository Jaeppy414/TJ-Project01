package banking;

public class NgtNumberException extends Exception{
	
	public NgtNumberException() {
		super("음수를 입력하지 마세요");
	}
}
