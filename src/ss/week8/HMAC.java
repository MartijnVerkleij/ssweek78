package ss.week8;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class HMAC {
	
	public static final String HMAC_SHA256 = "HmacSHA256";

	public HMAC() {
		try {
			simpleHMAC("s1375253");
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void simpleHMAC(String message) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance(HMAC_SHA256);
		byte[] keyBytes = "0xabababababababab".getBytes(Charset.forName("UTF-8"));
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA256);
		mac.init(signingKey);
		byte[] messageMac = mac.doFinal(message.getBytes(Charset.forName("UTF-8")));
		System.out.println(Hex.encodeHexString(messageMac));
	}

	public static void main(String[] args) {
		new HMAC();
	}
}
