package vn.onepay.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA256 {
	private Mac mac = null;

	public static HmacSHA256 getInstance(String secret) {
		return new HmacSHA256(secret);
	}

	public HmacSHA256(String secret) {
		SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(),
				"HmacSHA256");
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(
					"Invalid key exception while converting to HMac SHA256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String byteArrayToString(byte[] data) {
		BigInteger bigInteger = new BigInteger(1, data);
		String hash = bigInteger.toString(16);
		// Zero pad it
		while (hash.length() < 64) {
			hash = "0" + hash;
		}
		return hash;
	}

	public String sign(String data) {
		try {
			byte[] digest = mac.doFinal(data.getBytes("UTF-8"));
			return byteArrayToString(digest);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		HmacSHA256 hmacSHA256 = HmacSHA256.getInstance("178ry3q0wb3k7a374thztyt7uew2peba");
		String data = "access_key=adhjifaji5uv8cadmiul&pin=1322833278754&serial=31201844372&type=VIETTEL";
		String signature=hmacSHA256.sign(data);//c21082ff32165364b668d4c913d5c3b6de13f968d59953dc40cd1d59ca9ffdd6";
		String url = "http://api.1pay.vn/ws/cardcharging?"+data+"&signature="+signature;
		System.out.println(url);
	}
}
