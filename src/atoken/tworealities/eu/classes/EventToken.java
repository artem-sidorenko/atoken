package atoken.tworealities.eu.classes;

import android.database.Cursor;

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

	public EventToken(Cursor c) {
		super(c);
		// TODO Auto-generated constructor stub
		counter = c.getInt(c.getColumnIndex(DBAdapter.KEY_EVENT_COUNTER));
	}

}
