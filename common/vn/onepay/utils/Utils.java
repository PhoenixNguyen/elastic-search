package vn.onepay.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

public class Utils {

	public static int getQuarterOfDate(Date myDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		int month = cal.get(Calendar.MONTH); /* 0 through 11 */
		int quarter = (month / 3) + 1;
		return quarter;
	}
	
	public static Date getFirstDayOfQuarter(int quarter){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return getFirstDayOfQuarter(quarter, year);
	}
	
	public static Date getFirstDayOfQuarter(int quarter, int year){
		int month = 1;
		switch (quarter) {
		case 1:
			month =1;
			break;
		case 2:
			month = 4;
			break;
		case 3:
			month = 7;
			break;
		case 4:
			month = 10;
			break;
		default:
			break;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		// get start of the month
		return calendar.getTime();
	}
	public static Date getEndDayOfQuarter(int quarter){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return getEndDayOfQuarter(quarter, year);
	}
	public static Date getEndDayOfQuarter(int quarter, int year){
		int month = 3;
		switch (quarter) {
		case 1:
			month =3;
			break;
		case 2:
			month = 6;
			break;
		case 3:
			month = 9;
			break;
		case 4:
			month = 12;
			break;
		default:
			break;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month -1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		// get end of the month
		return calendar.getTime();
	}
	
	public static int getDaysOfQuarter(int quarter){
		Date firstDate = getFirstDayOfQuarter(quarter);
		Date endDate = getEndDayOfQuarter(quarter);
		return daysBetween(firstDate, endDate);
	}
	public static boolean isSameDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2
						.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}

	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		return sameDay;
	}
	public static Date getStartOfHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// start hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		return calendar.getTime();
	}
	public static Date getEndOfHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// end hour of day !
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 23, 59, 59);
		return calendar.getTime();
	}

	public static Date getStartOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
		// hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		// get start of this week in milliseconds
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}

	public static Date getEndOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
		// hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		// get start of this week in milliseconds
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		Calendar last = (Calendar) calendar.clone();
		last.add(Calendar.DAY_OF_YEAR, 6);
		last.set(Calendar.HOUR_OF_DAY, 23);
		last.set(Calendar.MINUTE, 59);
		last.set(Calendar.SECOND, 59);
		last.set(Calendar.MILLISECOND, 999);
		return last.getTime();
	}

	public static Date getStartOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		// get start of the month
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getEndOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		// get start of the month
		return calendar.getTime();
	}

	public static int daysBetween(Date fromDate, Date toDate) {
		return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static int hourOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int monthOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}
	public static int yearOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static Date dateAdd(Date date, int numOfDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, numOfDay);
		return calendar.getTime();
	}

	private static MessageDigest msgDigest = null;
	static {
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
		}
	}

	public static String encryptMD5(String message) {
		String digest = null;
		try {
			byte[] hash = msgDigest.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return digest;
	}

	// private static String
	// CHARACTERS="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz";

	public static String generateAccountSalt() {
		return RandomStringUtils.random(32, CHARACTERS);
	}

	public static String generateAccountAccessKey() {
		return RandomStringUtils.random(20, CHARACTERS);
	}

	public static String generateAccountSecret() {
		return RandomStringUtils.random(32, CHARACTERS);
	}

	public static String getAccountHashedPassword(String password, String salt) {
		String hashedPassword = Utils.encryptMD5(password + salt);
		return hashedPassword;
	}
	
	public static String[] mobileOperators = { "VNP", "VMS", "VTM", "SFE", "VNM", "BEL", "GATE", "VCOIN", "ZING", "UNKNOWN" };
	public static String[] mobileOperatorNames = { "Vinaphone", "Mobifone", "Viettel", "SFone", "Vietnamobile", "Beeline", "Gate - FPT", "VCoin - VTC", "Zing - VNG ", "Khác" };
	public String[] VNP = { "8491", "8494", "84123", "84124", "84125", "84127",
			"84129" };
	public String[] VMS = { "8490", "8493", "84121", "84122", "84126", "84128",
			"84120" };
	public String[] VTM = { "8498", "8497", "84165", "84166", "84167", "84168",
			"84169", "84164", "84163", "84162","8496" };
	public String[] SFE = { "8495" };
	public String[] VNM = { "8492", "84188", "84186" };
	public String[] BEL = { "84199", "84996" };
	public String[] GATE = {};
	public String[] VCOIN = {};
	public String[] ZING = {};
	public String[] UNKNOWN = {};

	public static String findMobileOperator(String mobileNumber) {
		mobileNumber = getFormatedMsisdn(mobileNumber);
		String result = null;
		for (int i = 0; i < mobileOperators.length; i++) {
			try {
				Utils util = new Utils();
				String[] short_number = (String[]) Utils.class.getField(
						mobileOperators[i]).get(util);
				for (int j = 0; j < short_number.length; j++) {
					if (mobileNumber.startsWith(short_number[j])) {
						return mobileOperatorNames[i];
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String findMobileOperatorCode(String mobileNumber) {
		mobileNumber = getFormatedMsisdn(mobileNumber);
		String result = null;
		for (int i = 0; i < mobileOperators.length; i++) {
			try {
				Utils util = new Utils();
				String[] short_number = (String[]) Utils.class.getField(
						mobileOperators[i]).get(util);
				for (int j = 0; j < short_number.length; j++) {
					if (mobileNumber.startsWith(short_number[j])) {
						return mobileOperators[i];
					}
				}
			} catch (Exception e) {
				System.out.println(mobileNumber);
				e.printStackTrace();
			}
		}
		if (result == null)
			System.out.println(mobileNumber);
		return result;
	}
	
	public static String findMobileOperatorByCode(String code){
		for(int i = 0; i< mobileOperators.length; i ++){
			if(code.equalsIgnoreCase(mobileOperators[i]))
				return mobileOperatorNames[i];
		}
		return code;
	}

	public static Map sortByComparator(Map unsortMap, final boolean asc) {

		List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (asc)
					return ((Comparable) ((Map.Entry) (o1)).getValue())
							.compareTo(((Map.Entry) (o2)).getValue());
				else
					return ((Comparable) ((Map.Entry) (o2)).getValue())
							.compareTo(((Map.Entry) (o1)).getValue());
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


	public static Date convertToMongoDBTime(Date date) {
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 7);
			return cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date mongoDbTimeToDisplayTime(Date date){
		try{
			Calendar cal = Calendar.getInstance(); // creates calendar
		    cal.setTime(date); // sets calendar time/date
		    cal.add(Calendar.HOUR_OF_DAY, -7); // adds one hour
		    return cal.getTime(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	private final static Pattern IPPattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
	public static String getIP(HttpServletRequest req){
		String ip = req.getRemoteAddr();
		if (ip!=null && (ip.startsWith("127.") || ip.startsWith("192.168."))) {
			
			String userAgent = req.getHeader("User-Agent")!=null?req.getHeader("User-Agent"):req.getHeader("user-agent");
			if(StringUtils.isNotEmpty(userAgent)&&(userAgent.toLowerCase().indexOf("opera")!=-1)&&(userAgent.toLowerCase().indexOf("mini")!=-1)){
				ip=req.getHeader("HTTP_X_FORWARDED_FOR")!=null?req.getHeader("HTTP_X_FORWARDED_FOR"):req.getHeader("x-forwarded-for")!=null?req.getHeader("x-forwarded-for"):req.getHeader("X-Forwarded-For");
			}else{
				ip = StringUtils.isNotEmpty(req.getHeader("vx-forwarded-for"))?req.getHeader("vx-forwarded-for"): req.getHeader("x-forwarded-for")!=null?req.getHeader("x-forwarded-for"):req.getHeader("X-Forwarded-For");
			}
			if (ip != null) {
				Matcher m = IPPattern.matcher(ip);
				if (m.find()) {
					ip = m.group(0);
				} else
					ip = null;
			}else{
				ip = req.getRemoteAddr();
			}
			
		}
		return ip;
	}
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
			"[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	public static boolean validateEmail(String email){
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		if(matcher.find())
			return true;
		return false;
	}
	
	public static String numberToText(String number)
	{
		number=number.replaceAll("\\D+","");
	    String[] dv = { "", "mươi", "trăm", "nghìn", "triệu", "tỉ"};
	    String[] cs = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };
	    String doc;
	    int i, j, k, n, len, found, ddv, rd;

	    len = number.length();
	    number += "ss";
	    doc = "";
	    found=0;
	    ddv=0;
	    rd=0;

	    i = 0;
	    while (i < len)
	    {
	        //So chu so o hang dang duyet
	        n = (len - i + 2) % 3 + 1;

	        //Kiem tra so 0
	        found = 0;
	        for (j = 0; j < n; j++)
	        {
	            if (number.charAt(i + j) != '0')
	            {
	                found = 1;
	                break;
	            }
	        }

	        //Duyet n chu so
	        if (found == 1)
	        {
	            rd = 1;
	            for (j = 0; j < n; j++)
	            {
	                ddv=1;
	                switch(number.charAt(i+j))
	                {
	                    case '0':
	                        if (n-j==3) doc+=cs[0]+" ";
	                        if (n-j==2)
	                        {
	                            if (number.charAt(i+j+1)!='0') doc+="lẻ ";
	                            ddv=0;
	                        }
	                        break;
	                    case '1':
	                        if (n-j==3) doc+=cs[1] + " ";
	                        if (n-j==2)
	                        {
	                            doc+="mười ";
	                            ddv=0;
	                        }
	                        if (n-j==1)
	                        {
	                            if (i + j == 0) k = 0;
	                            else k = i + j - 1;

	                            if (number.charAt(k)!='1' && number.charAt(k)!='0')
	                                doc += "mốt ";
	                            else
	                                doc+=cs[1]+" ";
	                        }
	                        break;
	                    case '5':
	                        if (i+j==len-1)
	                            doc+="lăm ";
	                        else
	                            doc+=cs[5]+" ";
	                        break;
	                    default:
	                        doc+=cs[(int)number.charAt(i+j)-48]+" ";
	                        break;
	                }

	                 //Doc don vi nho
	                if (ddv==1)
	                {
	                    doc+=dv[n-j-1]+" ";
	                }
	            }
	        }
	        

	        //Doc don vi lon
	        if (len-i-n>0)
	        {
	            if ((len - i - n ) % 9 == 0)
	            {
	                if (rd==1)
	                    for (k = 0; k < (len - i - n) / 9; k++)
	                        doc += "tỉ ";
	                rd=0;
	            }
	            else
	                if (found!=0) doc+=dv[((len-i-n+1)%9)/3+2]+" ";
	        }

	        i+=n;
	    }

	    if (len == 1)
	        if (number.charAt(0) == '0' || number.charAt(0) == '5') return cs[(int)number.charAt(0) - 48];
	    
	    doc = Character.toUpperCase(doc.charAt(0)) + doc.substring(1);
	    return doc;
	}
	public static String formatNumber(double number) {
//		String pattern = "###,###.##";
		String pattern = "###,###";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(number);
		return output;
	}
	public static String formatNumber(float number) {
		double value = (double) number;
//		String pattern = "###,###.##";
		String pattern = "###,###";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(value);
		return output;
	}
	public static String numberToText(float number) {
		return numberToText(formatNumber(number));
	}
	public static String numberToText(double number) {
		return numberToText(formatNumber(number));
	}

	public static long doubleToLong( double d ) {
		// mashing it all into one function of methods
		return new Long( String.format("%.0f", d) ).longValue();
	}
	public static double longToDouble( long l ) {
		// mashing it all into one function of methods
		return (double)l;
	}
	public static void printMap(Map<String, String> map) {
		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
		}
	}
	
	public static String getFormatedMsisdn(String msisdn){
		msisdn = StringUtils.trimToEmpty(msisdn);
		msisdn =  StringUtils.trimToEmpty(msisdn.replaceAll("\\D+",""));
		if(StringUtils.isNotEmpty(msisdn)){
			if(msisdn.startsWith("84"))
				return msisdn;
			else
				msisdn = "84" + (msisdn.startsWith("0")?msisdn.substring(1):msisdn);
		}
		return StringUtils.trimToEmpty(msisdn);
	}
	
	public static int wordCount(String s){

	    int wordCount = 0;

	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) {
	        // if the char is a letter, word = true.
	        if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
	            word = true;
	            // if char isn't a letter and there have been letters before,
	            // counter goes up.
	        } else if (!Character.isLetter(s.charAt(i)) && word) {
	            wordCount++;
	            word = false;
	            // last word of String; if it doesn't end with a non letter, it
	            // wouldn't count without this.
	        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
	            wordCount++;
	        }
	    }
	    return wordCount;
	}
	
	public static void main(String[] args) throws Exception {
//		 String pass = RandomStringUtils.random(32, 0, 0, true, true, null,new SecureRandom());
//		 pass = RandomStringUtils.random(32, CHARACTERS);
//		 System.out.println(pass);
//		 String raw_password = "123456";
//		 String salt = "10rcqkomec5krvn5xctf8vyuwwzqc0ta";
//		 String hashedPwd = encryptMD5(raw_password + salt);
//		 System.out.println(hashedPwd);
//		 DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		 Date date = getFirstDayOfQuarter(1);
//		 System.out.println(dtFormat.format(date));
//		 date = getEndDayOfQuarter(1);
//		 System.out.println(dtFormat.format(date));
		String message = "10|The chua duoc kich hoat hoac khong ton tai";
		if(message.indexOf("|")!=-1){
			message = message.substring(message.indexOf("|")+1);
		}
		System.out.println(VnUtils.removeTone(message));
		System.out.println(wordCount(message));
	}
}
