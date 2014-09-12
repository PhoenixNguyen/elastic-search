package vn.onepay.cache.dynacache.impl;

import vn.onepay.cache.dynacache.DynaCacheItemFeed;
import vn.onepay.cache.dynacache.DynaCacheItemFeedException;

public class HttpCacheItemAsyncFeedImpl extends HttpCacheItemFeedImpl implements DynaCacheItemFeed {
	
	int timeOut=5000;
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	//-----------------
	@Override
	public Object feedItem(Object key) throws DynaCacheItemFeedException {
		// TODO Auto-generated method stub
		return ParalellCacheItemFeedHelper.feedItem(key, this, timeOut);
	}
}
