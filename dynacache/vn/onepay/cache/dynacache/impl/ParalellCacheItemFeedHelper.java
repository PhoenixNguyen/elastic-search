package vn.onepay.cache.dynacache.impl;

import vn.onepay.cache.dynacache.DynaCacheItemFeed;


public class ParalellCacheItemFeedHelper extends Thread{
	
	Object result=null;
	Object locker;
	Object key;
	DynaCacheItemFeed dynaCacheItemFeed;
	
	public ParalellCacheItemFeedHelper(Object key, DynaCacheItemFeed dynaCacheItemFeed, Object locker){
		this.key=key;
		this.dynaCacheItemFeed=dynaCacheItemFeed;
		this.locker=locker;
	}
	
	@Override
	public void run() {
		try {
			result = dynaCacheItemFeed.feed(key);
			if (locker != null) {
				synchronized (locker) {
					locker.notify();
				}
			}
		} catch (Exception e) {
			result=null;
			e.printStackTrace();
		}
	}
	
	public static Object feedItem(Object key, DynaCacheItemFeed dynaCacheItemFeed) {
		Object obj = new Object();
		ParalellCacheItemFeedHelper thread = new ParalellCacheItemFeedHelper(key, dynaCacheItemFeed, obj);
		thread.start();
		synchronized (obj) {
			try {
				obj.wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (thread.isAlive() || thread.result == null) {
			thread.interrupt();
		}
		return thread.result;
	}
	public static Object feedItem(Object key, DynaCacheItemFeed dynaCacheItemFeed, final int _timeout) {
		Object obj = new Object();
		ParalellCacheItemFeedHelper thread = new ParalellCacheItemFeedHelper(key, dynaCacheItemFeed, obj);
		thread.start();
		synchronized (obj) {
			try {
				obj.wait((_timeout>1000?_timeout:1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (thread.isAlive() || thread.result == null) {
			thread.interrupt();
		}
		return thread.result;
	}
}
