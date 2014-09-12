package vn.onepay.common;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SharedConstants {
	public final static String ONEPAY_DATABASE_SCHEMA="1pay";
	public final static String CONSOLE_DATABASE_SCHEMA="console";
	public final static String WALLET_DATABASE_SCHEMA="wallet";
	
	public final static String DEFAULT_MERCHANT ="1pay";
	
	public final static String OTP_AUTH_ON_OFF_STATUS = "ON";
	public final static String ACCOUNT_OTP_AUTH_OBJECT = "account_otp_auth_session_obj";
	public final static String ACCOUNT_VERIFIED_OTP = "account_verified_otp";
	
	public final static String AUTH_TICKET_ENCRIPT_CODE="0123456789abcdef";
	public final static String AUTH_COOKIE_TICKET_NAME="auth_ticket";
	
	public final static String ACCOUNT_LOGINED = "account_logined";
//	public final static String ACCOUNT_LOGINED_PROFILES = "account_logined_profiles";
	
	public final static String ACCOUNT_LOGINED_LEVEL = "account_logined_level";
	public final static String ACCOUNT_LOGINED_EXP = "account_logined_exp";

	public final static String WALLET_ACCOUNT_LOGINED= "wallet_account_logined";
	
	public final static String SMS_CHARGING_SERVICE_CODE="SMS";
	public final static String CARD_CHARGING_SERVICE_CODE="CARD";
	public final static String WAP_CHARGING_SERVICE_CODE="WAP";
	public final static String DIRECT_CHARGING_SERVICE_CODE="DIRECT";
	public final static String SUB_CHARGING_SERVICE_CODE="SUB";
	public final static String IAC_CHARGING_SERVICE_CODE="IAC";
	
	public final static String[] ALL_ACTIVED_CHARGING_SERVICES = {CARD_CHARGING_SERVICE_CODE, SMS_CHARGING_SERVICE_CODE, WAP_CHARGING_SERVICE_CODE, IAC_CHARGING_SERVICE_CODE};
	public final static String[] ALL_CHARGING_SERVICES = {CARD_CHARGING_SERVICE_CODE, SMS_CHARGING_SERVICE_CODE, WAP_CHARGING_SERVICE_CODE, IAC_CHARGING_SERVICE_CODE, DIRECT_CHARGING_SERVICE_CODE, SUB_CHARGING_SERVICE_CODE};
	
	public static String[] MOBILE_OPERATORS = { "VNP", "VMS", "VTM", "VNM", "GATE", "VCOIN", "ZING"};
	public static List<Integer> CARD_PRICE_RANGES =  Arrays.asList(new Integer[]{0, 10000, 20000, 50000, 100000, 200000, 500000});
	public static List<Integer> SMS_PRICE_RANGES =  Arrays.asList(new Integer[]{500, 1000, 2000, 3000, 4000, 5000, 10000, 15000});
	public static List<Integer> WAP_PRICE_RANGES =  Arrays.asList(new Integer[]{500, 1000, 2000, 3000, 4000, 5000, 10000, 15000});//demo
	public static List<Integer> IAC_PRICE_RANGES =  Arrays.asList(new Integer[]{1000, 2000, 3000, 4000, 5000, 10000, 15000, 20000, 30000, 40000, 50000, 100000});//demo

	public final static byte PAYMENT_PROVIDER_DISABLE_STATUS 	= 0;
	public final static byte PAYMENT_PROVIDER_ENABLE_STATUS 	= 1;
	
	public final static String[] DEPARTMENTS= new String[]{"SU0", "SU1", "SU2", "SU3", "SU4"};
	public final static String DEFAULT_ACCOUNT_OWNER="huydq";
	public final static String MERCHANT_CONTRACT_SESSION = "merchant_contract_session";
	
	public static String PRODUCT_ICON_FOLDER 	= "/Users/hahm/working/my-workspace/1pay/WebContent/download/product/icon";
	public static String PRODUCT_ICON_HOST 		= "http://localhost:8080/download/product/icon";
	
	//------------
	public static boolean MBIZ=false;
	public static String[] MBIZ_MERCHANTS= null;
	public static double MBIZ_RATE=1.0d;
	//------------
}