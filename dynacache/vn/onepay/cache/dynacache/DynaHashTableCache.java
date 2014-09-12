/*
 * (C) Copyright IBM Corp. 2005  All rights reserved.
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
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Hoang Manh Ha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class DynaHashTableCache implements DynaCache{

	//private Logger logger = Logger.getLogger(DynaHashTableCache.class);
	
	private Hashtable<Object,CachedItem> items = new Hashtable<Object,CachedItem>();
	private long timeToLive = 5L*60*1000; //default, 5 minutes
	private int cacheSize = 50; //default
	private DynaCacheItemFeed feeder = null;

	/**
	 * 
	 */
	public DynaHashTableCache() {
//		super();
		// TODO Auto-generated constructor stub
		this(null);
	}

	/**
	 * 
	 * @param _feeder
	 */
	public DynaHashTableCache(DynaCacheItemFeed _feeder) {
		this(_feeder, 50, 5L*60*1000);
	}

	/**
	 * 
	 * @param _feeder
	 * @param _cacheSize
	 * @param _timeToLive
	 */
	public DynaHashTableCache(DynaCacheItemFeed _feeder, int _cacheSize, long _timeToLive) {
		feeder = _feeder;
		cacheSize = _cacheSize;
		timeToLive = _timeToLive;
	}
	
	/**
	 * @return
	 */
	public long getTimeToLive() {
		return timeToLive;
	}

	/**
	 * @param i
	 */
	public void setTimeToLive(long _timeToLive) {
		this.timeToLive = _timeToLive;
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
	 * @return
	 */
	public DynaCacheItemFeed getFeeder() {
		return feeder;
	}

	/**
	 * @param feed
	 */
	public void setFeeder(DynaCacheItemFeed feed) {
		feeder = feed;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see vn.xalo.cache.dynacache.IDynaCache#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return items.containsKey(key);
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
	 * @param defaultValue
	 * @return
	 */
	public synchronized Object getCachedItem(Object key, DynaCacheItemFeed _feeder) {
		if(key==null){
			//logger.info("Key to feed item is null");
			return null;
		}
		if(items.containsKey(key)){// in cache
			CachedItem cached = (CachedItem)items.get(key);
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
//						logger.info("Newer item fed");
						return o;
					}
					catch(DynaCacheItemFeedException e){
//						logger.info("Fail to feed newer item, out of date item returned");
						return cached.getItem();
					}
				}
			}
			else{
//				logger.info("Cache hit");
				return cached.getItem();
			}
		}
		else{// cache miss
//			logger.info("Cache miss");
			if(items.size() > cacheSize){// capacity exceeded
				//logger.info("Capacity exceeded");
				/*
				 * Since the capacity had exceeded we need to remove some items to get more space
				 * Here, for simplicity we remove the eldest item, however there exist some others ways to deal with this
				 * Some possible solutions are to remove the least accessed items, items exceeded time to live, ...  
				 */
				Enumeration<Object> e = items.keys();
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
				/*
				 * Ok, eldest item was removed
				 */
			}
			else{// do nothing
			}
			/*
			 * Feed new item
			 */
			if(_feeder == null){// don't know how to feed item
//				logger.info("No feeder available");
				return null;
			}
			else{
				try {
					Object o = _feeder.feedItem(key);
					items.put(key, new CachedItem(o, Calendar.getInstance().getTime().getTime()));
//					logger.info("Item fed");
					return o;
				} catch (DynaCacheItemFeedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					logger.info("Fail to feed item");
					return null;
				}
			}
		}
	}

	@Override
	public void removeCachedItem(Object key) {
		// TODO Auto-generated method stub
		if(items.containsKey(key))
			items.remove(key);
	}
	/**
	 * 
	 */
	@Override
	public synchronized void clearCache() {
		items.clear();
	}
}
