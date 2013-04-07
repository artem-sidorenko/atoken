package atoken.tworealities.eu.classes;

import android.database.Cursor;

public class TimeToken extends Token {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2731298503101800964L;
	private int type;

	@Override
	public String getNewOtp() {
		// TODO Auto-generated method stub
		this.otp = "000000";
		return "000000";
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
