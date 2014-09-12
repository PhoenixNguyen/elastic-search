package vn.onepay.cache.memcached;

import java.math.BigInteger;
import java.security.MessageDigest;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MemCachedManager {
	
	public static final String BEAN_NAME = "memCachedManager";
//	private static final Logger logger = Logger
//			.getLogger(MemCachedManager.class);
	private static MessageDigest msgDigest = null;
	static {
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
		}
	}
	private static final String NAMESPACE = "SACHARYA:5d41402abc4b2a76b9719d91101-";
	private static MemcachedClient[] m = null;
	private static final int NUMBER_OF_CLIENT = 20;

	public MemCachedManager(String memcachedServer) {
		try {
			m = new MemcachedClient[NUMBER_OF_CLIENT];
			for (int i = 0; i < NUMBER_OF_CLIENT; i++) {
				MemcachedClient c = new MemcachedClient(
						new BinaryConnectionFactory(), AddrUtil
								.getAddresses(memcachedServer));
				m[i] = c;
			}
		} catch (Exception e) {
			//logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void set(String key, int ttl, final Object o) {
		if (key == null || o == null)
			return;
		String encodeKey = key;
		try {
			encodeKey = encodeMD5(key);
		} catch (Exception exp) {
			encodeKey = key;
		}
		try {
			getMemcachedClient().set(NAMESPACE + encodeKey, ttl, o);
		} catch (Exception e) {
			//logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public Object get(String key) {
		Object o = null;
		String encodeKey = key;
		try {
			encodeKey = encodeMD5(key);
		} catch (Exception exp) {
			encodeKey = key;
		}
		try {
			o = getMemcachedClient().get(NAMESPACE + encodeKey);
//			if (o == null) {
//				System.out.println("Cache MISS for KEY: " + key);
//			} else {
//				System.out.println("Cache HIT for KEY: " + key);
//			}
		} catch (Exception e) {
			//logger.error(e.getMessage());
			e.printStackTrace();
		}
		return o;
	}

	public Object delete(String key) {
		String encodeKey = key;
		try {
			encodeKey = encodeMD5(key);
		} catch (Exception exp) {
			encodeKey = key;
		}
		try {
			return getMemcachedClient().delete(NAMESPACE + encodeKey);
		} catch (Exception e) {
			//logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public MemcachedClient getMemcachedClient() {
		MemcachedClient c = null;
		try {
			int i = (int) (Math.random() * NUMBER_OF_CLIENT);
			while ((i < 0) || (i >= NUMBER_OF_CLIENT))
				i = (int) (Math.random() * NUMBER_OF_CLIENT);
			c = m[i];
		} catch (Exception e) {
			//logger.error(e.getMessage());
			e.printStackTrace();
		}
		return c;
	}

	public String encodeMD5(String message) {
		try {
//			System.out.println("KEY: " + message);
			return new BigInteger(1, msgDigest.digest((message.replaceAll(
					"\\s+", "_")).getBytes())).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
