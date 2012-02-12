package atoken.tworealities.eu.classes;

public class EventToken extends Token {
	public EventToken(String name, String serial, String seed) {
		super(name, serial, seed);
		// TODO Auto-generated constructor stub
	}

	private int counter;
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public int getOtp() {
		// TODO Auto-generated method stub
		return 0;
	}

}
