package vn.onepay.cache.dynacache;

/**
 * @author Hoang Manh Ha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class ParalellDynaCache extends Thread{
	Object result=null;
	Object locker;
	Object key;
	DynaCache dynaCache;
	public ParalellDynaCache(Object key, DynaCache dynaCache, Object locker){
		this.key=key;
		this.dynaCache=dynaCache;
		this.locker=locker;
	}
	
	@Override
	public void run() {
		try {
			result = dynaCache.getCachedItem(key);
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
	
	public static Object feed(Object key, DynaCache dynaCache) {
		Object obj = new Object();
		ParalellDynaCache thread = new ParalellDynaCache(key, dynaCache, obj);
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
	public static Object feed(Object key, DynaCache dynaCache, final int _timeout) {
		Object obj = new Object();
		ParalellDynaCache thread = new ParalellDynaCache(key, dynaCache, obj);
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
