package vn.onepay.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CookieUtil {
	// The path the cookie will be available to, edit this to use a different
	// cookie path.
	private static final String COOKIE_PATH = "/";
	// Two years in seconds.
	private static final int COOKIE_USER_PERSISTENCE = 63072000;
	// cookie version
	private static final int COOKIE_VERSION = 1;

	private static List<Pattern> unSupportCookiePaterns = new ArrayList<Pattern>();
	static {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							CookieUtil.class
									.getResourceAsStream("/vn/onepay/utils/unspportcookie.txt"),
							"UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!StringUtils.isEmpty(line)) {
					Pattern pCookieUnSupport = Pattern.compile(StringUtils
							.trimToEmpty(line), Pattern.CASE_INSENSITIVE
							| Pattern.MULTILINE | Pattern.DOTALL);
					unSupportCookiePaterns.add(pCookieUnSupport);
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static boolean isVNXX(String str) {
		if (str == null)
			return false;
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) > 128)
				return false;
		return true;
	}

	public static boolean checkSupportCookie(HttpServletRequest request) {
		try {
			String userAgent = null;
			/*
			 * Catch headers names from the more specific to less.
			 */
			// Catch Request Parameter
			if (request.getParameter("UA") != null) {
				userAgent = request.getParameter("UA");
			}
			// Catch several known Transcoder/Proxy and similars.
			// TODO Move this logic to Matcher in future version.
			else if (request.getHeader("X-Skyfire-Version") != null) {
				userAgent = "Generic_Skyfire_Browser";
			} else if (request.getHeader("x-device-user-agent") != null) {
				userAgent = request.getHeader("x-device-user-agent");
			} else if (request.getHeader("X-OperaMini-Phone-UA") != null) {
				userAgent = request.getHeader("X-OperaMini-Phone-UA");
			}
			// Catch standard user-agent
			else {
				userAgent = request.getHeader("User-Agent");
			}
			if (userAgent != null) {
				for (Pattern pattern : unSupportCookiePaterns) {
					Matcher mUserAgent = pattern.matcher(userAgent);
					if (mUserAgent.find()) {
						System.err.println(userAgent + ": FALSE");
						return false;
					}
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return true;
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie != null && name.equals(cookie.getName())) {
						String cvalue = cookie.getValue();
						if (!StringUtils.isEmpty(cvalue)
								&& cvalue.startsWith("x6")) {
							cvalue = new String(Base64.decode(cvalue
									.substring(2)), "UTF-8");
						}
						return cvalue;
					}
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

	public static void setCookieValue(HttpServletResponse response,
			String name, String value, int maxAge) {
		try {
			// System.out.println("SET COOKIE "+name+"="+value);
			if (!StringUtils.isEmpty(value)) {
				for (int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					if (c != '~' && !Character.isLetterOrDigit(c)) {
						value = "x6" + Base64.encodeString(value);
						break;
					}
				}
			}

			maxAge = maxAge == Integer.MAX_VALUE ? COOKIE_USER_PERSISTENCE
					: maxAge;
			Cookie cookie = new Cookie(name, value);
			cookie.setVersion(COOKIE_VERSION);
			cookie.setMaxAge(maxAge);
			cookie.setPath(COOKIE_PATH);
			response.addCookie(cookie);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		setCookieValue(response, name, null, 0);
	}

	public static void setCookieValue(HttpServletResponse response,
			String name, String value, int maxAge, String domain) {
		try {
			if (StringUtils.isEmpty(value))
				return;
			if (!StringUtils.isEmpty(value) && (!value.startsWith("x6"))) {
				for (int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					if (c != '~' && !Character.isLetterOrDigit(c)) {
						value = "x6" + Base64.encodeString(value);
						break;
					}
				}
			}

			maxAge = maxAge == Integer.MAX_VALUE ? COOKIE_USER_PERSISTENCE
					: maxAge;
			Cookie cookie = new Cookie(name, value);
			cookie.setVersion(COOKIE_VERSION);
			cookie.setMaxAge(maxAge);
			cookie.setDomain(domain);
			cookie.setPath(COOKIE_PATH);
			response.addCookie(cookie);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static void removeCookie(HttpServletResponse response, String name,
			String domain) {
		try {
			Cookie cookie = new Cookie(name, null);
			cookie.setVersion(COOKIE_VERSION);
			cookie.setMaxAge(0);
			cookie.setDomain(domain);
			cookie.setPath(COOKIE_PATH);
			response.addCookie(cookie);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static String implode(String[] ary, String delim) {
		String out = "";
		for (int i = 0; i < ary.length; i++) {
			if (i != 0) {
				out += delim;
			}
			out += ary[i];
		}
		return out;
	}

	public static boolean isURLEncoded(String url) {
		if (StringUtils.isEmpty(url))
			return false;
		String testUrl = url;
		try {
			if (URLDecoder.decode(testUrl, "UTF-8") != testUrl) {
				testUrl = URLDecoder.decode(testUrl, "UTF-8");
			}
			if (URLEncoder.encode(testUrl, "UTF-8").equals(url))
				return true;
		} catch (Exception uncatchExp) {

		}
		return false;
	}

	public static boolean checkInitParameterOfServletContext(
			ServletContext servletContext, String paramName) {
		try {
			for (Enumeration<?> e = servletContext.getInitParameterNames(); e
					.hasMoreElements();) {
				String parameterName = (String) e.nextElement();
				if (parameterName.equalsIgnoreCase(paramName))
					return true;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return false;
	}

	public static boolean checkInitParameterOfHttpServlet(
			HttpServlet httpServlet, String paramName) {
		try {
			for (Enumeration<?> e = httpServlet.getInitParameterNames(); e
					.hasMoreElements();) {
				String parameterName = (String) e.nextElement();
				if (parameterName.equalsIgnoreCase(paramName))
					return true;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws Exception {

	}
}
