/*
 * US Government Users Restricted Rights Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 * The program is provided "as is" without any warranty express or
 * implied, including the warranty of non-infringement and the implied
 * warranties of merchantibility and fitness for a particular purpose.
 * IBM will not be liable for any damages suffered by you as a result
 * of using the Program. In no event will IBM be liable for any
 * special, indirect or consequential damages or lost profits even if
 * IBM has been advised of the possibility of their occurrence. IBM
 * will not be liable for any third party claims against you.
 * Created on Jan 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package vn.onepay.cache.dynacache;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Calendar;

import vn.onepay.cache.exception.DynaCacheException;
import vn.onepay.cache.memcached.MemCachedManager;


/**
 * @author Hoang Manh Ha
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class PassiveDynaMemoryCache implements PassiveDynaCache {

	///private Logger logger = Logger.getLogger(PassiveDynaMemoryCache.class);

	private static MessageDigest msgDigest = null;
	static {
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
		}
	}
	private MemCachedManager memCachedManager = null;
//	private long realTimeToLive = 60L * 60 * 24 * 1000; // default, 24 hours
	private int cacheSize = 500; // default

	public void setMemCachedManager(MemCachedManager memCachedManager) {
		this.memCachedManager = memCachedManager;
	}

	public MemCachedManager getMemCachedManager() {
		return memCachedManager;
	}

	/**
	 * 
	 */
	public PassiveDynaMemoryCache() {
		// super();
		this(500);
	}

	/**
	 * 
	 * @param _feeder
	 * @param _cacheSize
	 * @param _timeToLive
	 */
	public PassiveDynaMemoryCache(int _cacheSize) {
		cacheSize = _cacheSize;
	}

	public PassiveDynaMemoryCache(int _cacheSize, long rttl) {
		cacheSize = _cacheSize;
//		this.realTimeToLive = rttl;
	}

	 
	/**
	 * @return
	 */
	public int getCacheSize() {
		return cacheSize;
	}

	/**
	 * @param i
	 */
	public void setCacheSize(int i) {
		cacheSize = i;
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws DynaCacheException
	 */
	@Override
	public synchronized boolean containsKey(Object key) {
		return this.memCachedManager.get(encodeMD5(key.toString())) != null ? true : false;
	}

	/**
	 * 
	 * @param key
	 * @throws DynaCacheException
	 */
	@Override
	public synchronized void removeCachedItem(Object key) {
		this.memCachedManager.delete(encodeMD5(key.toString()));
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public synchronized Object getCachedItem(Object key) {
		//return null;
		try {
			CachedItem cached = (CachedItem) this.memCachedManager.get(encodeMD5(key.toString()));
			if (cached == null) {
				//System.out.println("getCachedItem(): Cache miss, key = " + key.toString());
				return null;
			} else {
				// kiem tra timeout cua ca 2, memCachedManager va cacheItem
				long currentTime = Calendar.getInstance().getTime().getTime();
				if ((cached.getLastUpdatedTime() + cached.getTimeToLive()) < currentTime) {
					//logger.info("Cache miss, key = " + key.toString());
					//System.out.println("getCachedItem(): Cache miss, key = " + key.toString());
					removeCachedItem(key);
					return null;
				}
				//System.out.println("getCachedItem(): Cache hit, key = " + key.toString());
				//logger.info("getCachedItem(): Cache hit, key = " + key.toString());
				return cached.getItem();
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public synchronized void setCachedItem(Object key, Object value, int timeToLive) {
		if (value != null) {
			try {
				memCachedManager.set(encodeMD5(key.toString()), timeToLive, new CachedItem(value, timeToLive * 1000, Calendar.getInstance()
						.getTime().getTime()));
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}
	
	@Override
	public void updateCachedItem(Object key, Object value) {
		try {
			int timeToLive = 0;
			CachedItem cached = (CachedItem) this.memCachedManager.get(encodeMD5(key.toString()));
			if(cached == null) return;
			
			try {
				timeToLive = (int) ((cached.getTimeToLive() - (Calendar.getInstance().getTime().getTime() - cached.getLastUpdatedTime()))/1000);
			} catch (Exception e) {
			}
			
			if(timeToLive <=0 ) return;
			
			setCachedItem(key, value, timeToLive);
		} catch (Exception e) {
		}
	}

	private String encodeMD5(String message) {
		try {
			return new BigInteger(1, msgDigest.digest((message).getBytes("UTF-8"))).toString(16);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
	}
}
