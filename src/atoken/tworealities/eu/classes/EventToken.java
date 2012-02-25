package atoken.tworealities.eu.classes;

import android.database.Cursor;

public class EventToken extends Token {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6380321267677699791L;
	private int counter;
	
	public EventToken(String name, String serial, String seed) {
		super(name, serial, seed);
		// TODO Auto-generated constructor stub
	}
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public String getOtp() {
		return "0000000";
	}

	public EventToken(Cursor c) {
		super(c);
		// TODO Auto-generated constructor stub
		counter = c.getInt(c.getColumnIndex(DBAdapter.KEY_EVENT_COUNTER));
	}

}
