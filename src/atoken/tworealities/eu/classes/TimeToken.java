package atoken.tworealities.eu.classes;

import android.database.Cursor;

public class TimeToken extends Token {
	private int type;

	@Override
	public int getOtp() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getType() {
		return type;
	}

	public TimeToken(String name, String serial, String seed, int type) {
		super(name, serial, seed);
		// TODO Auto-generated constructor stub
		this.type = type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public TimeToken(Cursor c) {
		super(c);
		// TODO Auto-generated constructor stub
		type = c.getInt(c.getColumnIndex(DBAdapter.KEY_TIME_TYPE));
	}

}
