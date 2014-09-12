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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import vn.onepay.cache.exception.DynaCacheException;


/**
 * @author Hoang Manh Ha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class PassiveDynaHashTableCache implements PassiveDynaCache{

	//private Logger logger = Logger.getLogger(PassiveDynaHashTableCache.class);
	
	private Hashtable<Object, CachedItem> items = new Hashtable<Object, CachedItem>();
	// private long timeToLive = 12L*60*1000; //default, 12 minutes
	private int cacheSize = 100; //default

	/**
	 * 
	 */
	public PassiveDynaHashTableCache() {
		//		super();
		// TODO Auto-generated constructor stub
		this(100);
	}

	/**
	 * 
	 * @param _feeder
	 * @param _cacheSize
	 * @param _timeToLive
	 */ 
	public PassiveDynaHashTableCache(int _cacheSize) {
		cacheSize = _cacheSize; 
	}
	 
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
	public synchronized boolean containsKey(Object key){
		return items.containsKey(key);
	}
	/**
	 * 
	 * @param key
	 * @throws DynaCacheException
	 */
	@Override
	public synchronized void removeCachedItem(Object key) {
		if(items.containsKey(key))
			items.remove(key);
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public synchronized Object getCachedItem(Object key) {
		try {
			if(items.containsKey(key)){// in cache
				CachedItem cached = (CachedItem)items.get(key);
				long currentTime = Calendar.getInstance().getTime().getTime();
//				logger.info("CachedKey = "+ key);
//				logger.info("Last updated time = " + cached.getLastUpdatedTime()+" ms");
//				logger.info("Time to live = " + cached.getTimeToLive()+" ms");
//				logger.info("Current time = " + currentTime+" ms");
//				logger.info("Outlive time = "+((cached.getLastUpdatedTime() + cached.getTimeToLive()) - currentTime)+" ms");
				if((cached.getLastUpdatedTime() + cached.getTimeToLive()) < currentTime){// cache out of date
					//logger.info("Cache out of date");
					removeCachedItem(key);
					return null;
				}
				else{
					//logger.info("Cache hit");
					//System.outut.println("Cache hit: "+key.toString());
					return CommonUtil.cloneViaSerialization((Serializable) cached.getItem());
				}
			}
			else{// cache miss
				//logger.info("Cache miss");
				//System.out.out.println("Cache miss: "+key.toString());
				return null;
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
	public synchronized void setCachedItem(Object key ,Object value, int timeToLive ) {
		try {
			if(items.size() > cacheSize){// capacity exceeded
				//logger.info("Capacity exceeded"); 
				Enumeration<?> e = items.keys();
				Object eldestKey = e.nextElement();
				long eldestAge = ((CachedItem)items.get(eldestKey)).getLastUpdatedTime();
				while(e.hasMoreElements()){
					Object k = e.nextElement();
					CachedItem c = (CachedItem) items.get(k);
					if(eldestAge > c.getLastUpdatedTime()){// found elder
						eldestKey = k;
						eldestAge = c.getLastUpdatedTime();
					}
				}
				items.remove(eldestKey); 
			} 
			items.put(key, new CachedItem(CommonUtil.cloneViaSerialization((Serializable) value), 
										timeToLive*1000 ,Calendar.getInstance().getTime().getTime()));
		} catch (Exception e) {
			// do nothing
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public synchronized void updateCachedItem(Object key ,Object value) {
		if(!items.containsKey(key)) return;
		
		try {
			CachedItem cached = (CachedItem) items.get(key);
			cached.setItem(value);
			items.put(key, cached);
		} catch (Exception e) {
			// do nothing
		}
	}
	
	/**
	 * 
	 *
	 */
	@Override
	public synchronized void clearCache() {
		items.clear();
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	}
 
}
