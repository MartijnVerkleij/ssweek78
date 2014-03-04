package ss.week8;

import org.apache.commons.codec.binary.Base64;

public class ConfidentialityIntegrity {

	public ConfidentialityIntegrity() {
		cookieHack();
	}
	
	public void cookieHack() {
		BadCookieCrypto cookie = new BadCookieCrypto();
		String cookieString = cookie.createCookie();
		String last = "";
		for (int i = 4; i > 0; i--) {
			last += cookieString.charAt(cookieString.length() - i);
		}
		byte[] bytes = Base64.decodeBase64(last);
		byte lastByteEncrypt = (byte) ((byte) 'N' ^ bytes[2]);
		bytes[2] = (byte) ((byte) 'Y' ^ lastByteEncrypt);
		String newLast = Base64.encodeBase64String(bytes);
		cookieString = cookieString.replace(last, newLast);
		System.out.println(cookie.isAdmin(cookieString));
	}

	public static void main(String[] args) {
		new ConfidentialityIntegrity();
	}

}
