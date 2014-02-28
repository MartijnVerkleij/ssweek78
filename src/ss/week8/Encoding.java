package ss.week8;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.Base64;

public class Encoding {
	
	private int[] counter = new int[]{0, 0, 0, 0};
	
	public Encoding() {
		run();
	}
	
	public static void encode(String toConvert) {
		byte[] bytesToConvert = toConvert.getBytes(Charset.forName("UTF-8"));
		System.out.println(Hex.encodeHexString(bytesToConvert));
		System.out.println(Base64.encodeBase64String(bytesToConvert));
	}
	
	public static void reencode(String toConvert) {
		byte[] toReencode = Base64.decodeBase64(toConvert);
		System.out.println(Hex.encodeHexString(toReencode));
	}
	
	public static void hashAll(String toHash) throws NoSuchAlgorithmException {
		byte[] bytesToHash = toHash.getBytes(Charset.forName("UTF-8"));

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashedBytes = md.digest(bytesToHash);
		char[] hashed = Hex.encodeHex(hashedBytes);
		System.out.println("MD5:");
		System.out.println(hashed);
		System.out.println(" - " + hashed.length);

//		md = MessageDigest.getInstance("SHA1");
//		hashedBytes = md.digest(bytesToHash);
//		hashed = Hex.encodeHex(hashedBytes);
//		System.out.println("SHA1");
//		System.out.println(hashed);
//		System.out.println(" - " + hashed.length);
//
//		md = MessageDigest.getInstance("SHA-256");
//		hashedBytes = md.digest(bytesToHash);
//		hashed = Hex.encodeHex(hashedBytes);
//		System.out.println("SHA-256");
//		System.out.println(hashed);
//		System.out.println(" - " + hashed.length);
	}
	
	public void bruteForceAlice() throws NoSuchAlgorithmException {
		String[] lowercaseAlphabet = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", 
				"j", "k", "l", "m", "n", "o", "p", "q", "r", 
				"s", "t", "u", "v", "w", "x", "y", "z", };
		String guess = null;
		String alice = "c0af77cf8294ff93a5cdb2963ca9f038";
		MessageDigest md = MessageDigest.getInstance("MD5");
		long starttime = System.currentTimeMillis();
		
		// Initial guess
		guess = Hex.encodeHexString(md.digest("aaaa".getBytes(Charset.forName("UTF-8"))));
		while (!guess.equals(alice)) {
			counterTick();
			guess = lowercaseAlphabet[counter[0]] + lowercaseAlphabet[counter[1]] 
					+ lowercaseAlphabet[counter[2]] + lowercaseAlphabet[counter[3]];
			guess = Hex.encodeHexString(md.digest(guess.getBytes(Charset.forName("UTF-8"))));
		}
		System.out.println(System.currentTimeMillis() - starttime);
		System.out.println( lowercaseAlphabet[counter[0]] + lowercaseAlphabet[counter[1]] 
				+ lowercaseAlphabet[counter[2]] + lowercaseAlphabet[counter[3]]);
	}
	
	private void counterTick() {
		counter[0]++;
		if (!(counter[0] < 26)){
			counter[0] = 0;
			counter[1]++;
		}
		if (!(counter[1] < 26)){
			counter[1] = 0;
			counter[2]++;
		}
		if (!(counter[2] < 26)){
			counter[2] = 0;
			counter[3]++;
		}
		if (!(counter[3] < 26)){
			counter[3] = 0;
		}
	}

	public void run() {
// Basic encoding
//		encode("Security Intro");
//		reencode("AgICAgICAgICAgICAgICAgICAgICAgICAg==");
		
// Basic hashing		
//		try {
//			hashAll("password");
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		
// 8.9 Brute forcing Alice
		try {
			bruteForceAlice();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
// It takes 250 plusminus 10 ms
// Concentrated mostly around 244
	}
	
	public static void main(String[] args) {
		new Encoding();
	}
}
