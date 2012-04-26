package atoken.tworealities.eu.classes;

import android.database.Cursor;
import android.util.Log;

public class EventToken extends Token {
	private static final String TAG = EventToken.class.getSimpleName();
	private static final long serialVersionUID = -6380321267677699791L;
	private int counter;
	
	private static final int[] DIGITS_POWER
    // 0 1  2   3    4     5      6       7        8
    = {1,10,100,1000,10000,100000,1000000,10000000,100000000};
	
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
		/**
		 * @TODO CLEAN UP THIS SHIT
		 */
		byte[] bcounter = new byte[8];
		long movingFactor = (long) counter;
		
		for (int i = bcounter.length - 1; i >= 0; i--) {
			bcounter[i] = (byte) (movingFactor & 0xff);
			movingFactor >>= 8;
		}
		
		byte[] hash = Utils.hmacSha1(Utils.stringToHex(seed), bcounter);
		int offset = hash[hash.length - 1] & 0xf;
		
		int otpBinary = ((hash[offset] & 0x7f) << 24)
						|((hash[offset + 1] & 0xff) << 16)
						|((hash[offset + 2] & 0xff) << 8)
						|(hash[offset + 3] & 0xff);
		
		int otp = otpBinary % DIGITS_POWER[6];
		String result = Integer.toString(otp);
		
		
		while(result.length() < 6){
			result = "0" + result;
		}

		Log.d(TAG, "Generated event OTP "+result+" with counter "+counter);
		counter++;
		return result;
	}

	public EventToken(Cursor c) {
		super(c);
		// TODO Auto-generated constructor stub
		counter = c.getInt(c.getColumnIndex(DBAdapter.KEY_EVENT_COUNTER));
	}

}
