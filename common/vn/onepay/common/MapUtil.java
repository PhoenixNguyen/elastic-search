package vn.onepay.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class MapUtil {

	public static String mapToQueryString(Map<String, String> map) {
		map = sortMap(map);
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (StringUtils.isNotEmpty(key)) {
				if (stringBuilder.length() > 0) {
					stringBuilder.append("&");
				}
				stringBuilder.append((key != null ? key : ""));
				stringBuilder.append("=");
				stringBuilder.append(value != null ? value : "");
			}
		}
		return stringBuilder.toString();
	}

	public static Map<String, String> queryStringToMap(String input) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		String[] nameValuePairs = input.split("&");
		for (String nameValuePair : nameValuePairs) {
			int pos = nameValuePair.indexOf("=");
			if (pos != -1) {
				String name = nameValuePair.substring(0, pos);
				String value = nameValuePair.substring(pos + 1);
				if (StringUtils.isNotEmpty(name))
					params.put(name, value);
			}
		}
		return sortMap(params);
	}

	public static Map<String, String> httpRequestParameterToMap(
			HttpServletRequest req) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Enumeration<String> parameterNames = req.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = req.getParameter(paramName);
			map.put(paramName, paramValue);
		}
		return map;
	}

	public static String mapToHttpQueryString(Map<String, String> map) {
		map = sortMap(map);
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (StringUtils.isNotEmpty(key)) {
				if (stringBuilder.length() > 0) {
					stringBuilder.append("&");
				}
				try {
					stringBuilder.append((key != null ? URLEncoder.encode(key,
							"UTF-8") : ""));
					stringBuilder.append("=");
					stringBuilder.append(value != null ? URLEncoder.encode(
							value, "UTF-8") : "");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(
							"This method requires UTF-8 encoding support", e);
				}
			}
		}

		return stringBuilder.toString();
	}

	public static Map<String, String> httpQueryStringToMap(String input) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String[] nameValuePairs = input.split("&");
		for (String nameValuePair : nameValuePairs) {
			String[] nameValue = nameValuePair.split("=");
			try {
				String name = nameValue[0];
				String value = nameValue.length > 1 ? nameValue[1] : "";
				if (StringUtils.isNotEmpty(name))
					map.put(URLDecoder.decode(name, "UTF-8"),
							nameValue.length > 1 ? URLDecoder.decode(value,
									"UTF-8") : "");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"This method requires UTF-8 encoding support", e);
			}
		}
		return sortMap(map);
	}

	private static Map<String, String> sortMap(Map<String, String> unsortMap) {
		Map<String, String> treeMap = new TreeMap<String, String>(unsortMap);
		return treeMap;
	}

	public static Map sortMapByValues(Map unsortMap, final boolean desc) {

		List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (desc) {
					return ((Comparable) ((Map.Entry) (o2)).getValue())
							.compareTo(((Map.Entry) (o1)).getValue());
				} else {
					return ((Comparable) ((Map.Entry) (o1)).getValue())
							.compareTo(((Map.Entry) (o2)).getValue());
				}
			}
		});
		// put sorted list into map again
		// LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
//	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(
//			Map<K, V> map, final boolean desc) {
//		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(
//				map.entrySet());
//
//		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
//			@Override
//			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
//				if(desc)
//					return e2.getValue().compareTo(e1.getValue());
//				else 
//					return e1.getValue().compareTo(e2.getValue());
//			}
//		});
//
//		return sortedEntries;
//	}

	public static void main(String[] args) {
		String input = "a=";
		queryStringToMap(input);
	}
}