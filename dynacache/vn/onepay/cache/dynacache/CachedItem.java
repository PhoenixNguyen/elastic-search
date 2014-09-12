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
 * Created on Jan 13, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package vn.onepay.cache.dynacache;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Hoang Manh Ha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("serial")
class CachedItem implements Serializable{

	private long lastUpdatedTime = 0;
	private Object item = null;
	private long timeToLive = 5*60*1000; // default 5 minutes
	
	/**
	 * 
	 * @param _item
	 * @param _lastUpdatedTime
	 */
	public CachedItem(Object _item,  long timeToLive, long _lastUpdatedTime) {
		item = _item;
		lastUpdatedTime = _lastUpdatedTime;
		this.timeToLive = timeToLive;
	}
	
	public CachedItem(Object _item, long _lastUpdatedTime) {
		item = _item;
		lastUpdatedTime = _lastUpdatedTime;
	}
	
	
	public CachedItem(Object _item) {
		this(_item, Calendar.getInstance().getTime().getTime());
	}

	
	
	public long getTimeToLive() {
		return timeToLive;
	} 
	

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	} 

	/**
	 * 
	 * @param _item
	 */
	
	/**
	 * @return
	 */
	public Object getItem() {
		return item;
	}

	/**
	 * @return
	 */
	public long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param object
	 */
	public void setItem(Object object) {
		item = object;
	}

	/**
	 * @param date
	 */
	public void setLastUpdatedTime(long date) {
		lastUpdatedTime = date;
	}

}
