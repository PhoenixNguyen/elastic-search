package vn.onepay.cache.dynacache;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Hoang Manh Ha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class PassiveRedisMemoryCache implements PassiveDynaCache {
	//private Logger logger = Logger.getLogger(PassiveRedisMemoryCache.class);
	private RedisTemplate<Object, Object> redisTemplate;
	public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public boolean containsKey(Object key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public void removeCachedItem(Object key) {
		redisTemplate.delete(key);
	}

	@Override
	public Object getCachedItem(Object key) {
		try {
			CachedItem cached = (CachedItem) redisTemplate.opsForValue().get(key);
			if (cached == null) {
				//logger.info("getCachedItem(): Cache miss, key = " + key.toString());
				return null;
			} else {
				// kiem tra timeout cua ca 2, memCachedManager va cacheItem
				long currentTime = Calendar.getInstance().getTime().getTime();
//				logger.info("CachedKey = "+ key);
//				logger.info("Last updated time = " + cached.getLastUpdatedTime()+" ms");
//				logger.info("Time to live = " + cached.getTimeToLive()+" ms");
//				logger.info("Current time = " + currentTime+" ms");
//				logger.info("Outlive time = "+((cached.getLastUpdatedTime() + cached.getTimeToLive()) - currentTime)+" ms");
				if ((cached.getLastUpdatedTime() + cached.getTimeToLive()) < currentTime) {
					//logger.info("Cache miss, key = " + key.toString());
					//System.out.println("getCachedItem(): Cache miss, key = " + key.toString());
					removeCachedItem(key);
					return null;
				}
				//System.out.println("getCachedItem(): Cache hit, key = " + key.toString());
				//logger.info("Cache hit, key = " + key.toString());
				return cached.getItem();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setCachedItem(Object key, Object value, int timeToLive) {
		if (value != null) {
			try {
				CachedItem cachedItem = new CachedItem(value, timeToLive * 1000, Calendar.getInstance().getTime().getTime());
				redisTemplate.opsForValue().set(key, cachedItem, Long.valueOf(timeToLive + ""), TimeUnit.SECONDS);
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}
	}

	@Override
	public void updateCachedItem(Object key, Object value) {
		try {
			int timeToLive = 0;
			CachedItem cached = (CachedItem) redisTemplate.opsForValue().get(key);
			if(cached == null) return;
			
			try {
				timeToLive = (int) ((cached.getTimeToLive() - (Calendar.getInstance().getTime().getTime() - cached.getLastUpdatedTime()))/1000);
			} catch (Exception e) {
			}
			if(timeToLive <=0 ) return;
			setCachedItem(key, value, timeToLive);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearCache() {
		redisTemplate.getConnectionFactory().getConnection().flushAll();
	}

}
