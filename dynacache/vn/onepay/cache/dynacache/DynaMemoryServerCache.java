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
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class DynaMemoryServerCache implements DynaCache{
	
	//private Logger logger = Logger.getLogger(DynaMemoryServerCache.class);
	private static MessageDigest msgDigest = null;
	static {
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
		}
	}
	private MemCachedManager memCachedManager = null;
	private long timeToLive = 5L*60*1000; //default, 5 minutes
	private int cacheSize = 50; //default
	private DynaCacheItemFeed feeder = null;
	
	/**
	 * @return
	 */
	public long getTimeToLive() {
		return timeToLive;
	}

	/**
	 * @param i
	 */
	public void setTimeToLive(long i) {
		timeToLive = i;
	}
	
	public int getCacheSize() {
		return cacheSize;
	}
	
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	
	public MemCachedManager getMemCachedManager() {
		return memCachedManager;
	}
	
	public void setMemCachedManager(MemCachedManager memCachedManager) {
		this.memCachedManager = memCachedManager;
	}
	
	public DynaCacheItemFeed getFeeder() {
		return feeder;
	}
	public void setFeeder(DynaCacheItemFeed feeder) {
		this.feeder = feeder;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return this.memCachedManager.get(encodeMD5(key.toString()))!=null?true:false;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Object getCachedItem(Object key) {
		return getCachedItem(key, feeder);// default
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public synchronized Object getCachedItem(Object key,DynaCacheItemFeed _feeder) {
		if(key==null){
			//logger.info("Key to feed item is null");
			return null;
		}
		Object cacheItem=null;
		try{
			cacheItem=this.memCachedManager.get(encodeMD5(key.toString()));
		}catch(Exception e){
			e.printStackTrace();
		}
		if(cacheItem!=null){// in cache
			CachedItem cached = (CachedItem) cacheItem;
			long currentTime = Calendar.getInstance().getTime().getTime();
//			logger.info("Last updated time = " + cached.getLastUpdatedTime());
//			logger.info("Time to live = " + getTimeToLive());
//			logger.info("Current time = " + currentTime);
			if((cached.getLastUpdatedTime() + getTimeToLive()) < currentTime){// cache out of date
				//logger.info("Cache out of date");
				if(_feeder == null){// don't know how to feed item
					//logger.info("No feeder available");
					return null;
				}
				else{
					try{
						Object o = _feeder.feedItem(key);
						cached.setItem(o);
						cached.setLastUpdatedTime(Calendar.getInstance().getTime().getTime());
						//logger.info("Newer item fed");
						return o;
					}
					catch(DynaCacheItemFeedException e){
						//logger.info("Fail to feed newer item, out of date item returned");
						return cached.getItem();
					}
				}
			}
			else{
				//logger.info("Cache hit");
				return cached.getItem();
			}
		}
		else{// cache miss
			//logger.info("Cache miss");
			/*
			 * Feed new item
			 */
			if(_feeder == null){// don't know how to feed item
				//logger.info("No feeder available");
				return null;
			}
			else{
				try {
					Object o = _feeder.feedItem(key);
					if(o!=null){
						try{
							int ttl=(int)(timeToLive/1000);
							memCachedManager.set(encodeMD5(key.toString()), ttl, new CachedItem(o, Calendar.getInstance().getTime().getTime()));
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					//logger.info("Item fed");
					return o;
				} catch (DynaCacheItemFeedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//logger.info("Fail to feed item");
					return null;
				}
			}
		}
	}

	/**
	 * 
	 * @param key
	 * @throws DynaCacheException
	 */
	@Override
	public synchronized void removeCachedItem(Object key){
		this.memCachedManager.delete(encodeMD5(key.toString()));
	}
	@Override
	public synchronized void clearCache() {
		//do nothing
	}
	
	
	private String encodeMD5(String message){
		try {
			return new BigInteger(1, msgDigest.digest((message)
					.getBytes("UTF-8"))).toString(16);
		} catch (UnsupportedEncodingException e) {
			return null;
		} 
	}
	public static void main(String[] args) {
		long timeToLive = 5L*60*1000; //default, 5 minutes
		int ttl=(int)(timeToLive/1000);
		System.out.println(ttl);
	}
}
