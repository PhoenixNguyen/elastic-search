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


/**
 * @author Hoang Manh Ha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public interface PassiveDynaCache {
	String BEAN_NAME="passiveDynaCache";
	boolean containsKey(Object key);
	void removeCachedItem(Object key);
	Object getCachedItem(Object key);
	void setCachedItem(Object key, Object value, int timeToLive); // set timeToLive in second
	void updateCachedItem(Object key, Object value);
	void clearCache();
}