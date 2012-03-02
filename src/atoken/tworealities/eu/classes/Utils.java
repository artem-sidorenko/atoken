package atoken.tworealities.eu.classes;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
	/**
	 * @TODO
	 * */
	public static boolean isHex(String input){
		return input.matches("^[0-9A-Fa-f]+$");
	}
	
	public static byte[] stringToHex(String input){
		//implementation like in the RFC example
		byte[] converted = new BigInteger("10" + input,16).toByteArray();
		byte[] result = new byte[converted.length - 1];
		for (int i = 1; i < result.length; i++)
			result[i-1] = converted[i];
		return result;
	}
	
	public byte[] hmacSha1(byte[] key, byte[] counter){
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(new SecretKeySpec(key, "HmacSHA1"));
			return mac.doFinal(counter);
		} catch (GeneralSecurityException e){
			throw new UndeclaredThrowableException(e);
		}
	}
}
