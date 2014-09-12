package vn.onepay.cache.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Vector;
  
/**
 * doc
 * This class intends to add chained exception capability to JDK 1.3
 * please see: http://java.sun.com/j2se/1.4.2/docs/guide/lang/chained-exceptions.html
 * (this capability is available in JDK 1.4.
 * How to achive it:
 * using an array to store stacktrace of catched exception before.
 * @author nghiemlong
 * @version
 */

@SuppressWarnings("serial")
public class CacheException extends Exception {
	/**
   */
  public static final String CAUSED_BY = "Caused by: "; 
	
	Vector<String> stackTrace = null;
	
	

	/**
   */
  public CacheException() {
		super();
		stackTrace = new Vector<String>();
	}

	/**
   * @param msg
   */
  public CacheException(String msg) {
		super(msg);
		stackTrace = new Vector<String>();
	}

	/**
   * doc
   *
   * @param msg
   * @param e
   */
  public CacheException(String msg, Throwable e) {
		super(msg);
		stackTrace = new Vector<String>();
		myInitCause(e);
	}

	/**
   * @param e
   */
  public CacheException(Throwable e) {
		super(e.getMessage());
		stackTrace = new Vector<String>();
		myInitCause(e);
	}


	/**
   * print stack trace of an exception to String
   *
   * @param e: previous catched exception
   * @return
   */
  static public String stack2string(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();
	}

	
	// avoid initCause function of SUN JDK 1.4
	// init this to get chained exception trace from previous exception
	/**
   * @param cause
   */
  private void myInitCause(Throwable cause) {
		// print stacktrace of cause to a string and add to vector
		stackTrace.add(stack2string(cause));
		if (cause instanceof CacheException) {
			CacheException tapEx = (CacheException) cause;
			Enumeration<String> e = tapEx.stackTrace.elements();
			while (e.hasMoreElements()) {
				String str = (String) e.nextElement();
				stackTrace.add(str);
			}
		}
	}


	// print chained exception like in SUN JDK 1.4
	public void printStackTrace() {
		StringBuffer output = new StringBuffer();

		String thisException = stack2string(this);
		output.append(thisException);

		Enumeration<String> e = stackTrace.elements();
		while (e.hasMoreElements()) {
			String str = (String) e.nextElement();
			output.append(CacheException.CAUSED_BY);
			output.append(str);
		}
		// do not remove this one.
	}

}
