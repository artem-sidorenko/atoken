package atoken.tworealities.eu.classes;

abstract public class Token {
	private int id;
	private String name;
	private String serial;
	private String seed;
	
	public Token(){
		super();
	}
	
	public Token(String name, String serial, String seed) {
		super();
		this.name = name;
		this.serial = serial;
		this.seed = seed;
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
	
	abstract public int getOtp();
}
