package atoken.tworealities.eu.classes;

import java.io.Serializable;

import android.database.Cursor;

abstract public class Token implements Serializable {
	private static final long serialVersionUID = -8625397089956144788L;
	protected int id;
	protected String name;
	protected String serial;
	protected String seed;
	
	public Token(String name, String serial, String seed) {
		super();
		this.name = name;
		this.serial = serial;
		this.seed = seed;
	}
	
	public Token(Cursor c){
		super();
		id = c.getInt(c.getColumnIndex(DBAdapter.KEY_MAIN_ID));
		name = c.getString(c.getColumnIndex(DBAdapter.KEY_MAIN_NAME));
		serial = c.getString(c.getColumnIndex(DBAdapter.KEY_MAIN_SERIAL));
		seed = c.getString(c.getColumnIndex(DBAdapter.KEY_MAIN_SEED));
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	
	public String getSeed() {
		return seed;
	}
	
	public int getId() {
		return id;
	}
	
	abstract public String getOtp();

	abstract public String getNewOtp();

}
