package vn.onepay.cache.dynacache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;

public class CommonUtil {
	/**
	 * MD5 encode
	 */
	public static MessageDigest msgDigestMD5 = null;
	static {
		try {
			msgDigestMD5 = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
		}
	}

	public static String toMd5String(String s) {
		msgDigestMD5.update(s.getBytes(), 0, s.length());

		byte[] mdbytes = msgDigestMD5.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
		/*
		 * return new BigInteger(1, msgDigest.digest()) .toString(16);
		 */
	}

	public static Object cloneViaSerialization(Serializable obj)
			throws Exception {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream() {

			public synchronized byte[] toByteArray() {
				return buf;
			}
		};

		ObjectOutputStream out = new ObjectOutputStream(bytes);
		out.writeObject(obj);
		out.close();

		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(
				bytes.toByteArray()));
		Object objCopy = in.readObject();

		// no need to close the stream - we created it and now will be throwing
		// away...
		// in.close();

		return objCopy;
	}

	/**
	 * SHA256 encode
	 */
	public static MessageDigest msgDigestSHA256 = null;
	static {
		try {
			msgDigestSHA256 = MessageDigest.getInstance("SHA-256");
		} catch (Exception ex) {
		}
	}
	public static String getSHA256Hash(String s) {
		msgDigestSHA256.update(s.getBytes(), 0, s.length());
		
		byte[] mdbytes = msgDigestSHA256.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}
}
