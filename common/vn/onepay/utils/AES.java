package vn.onepay.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class AES {
	
	private static final String ALGORITHM = "AES";
	////////////////////////////////////////////////////////////////////////////
	private static Key generateKey(byte[] keyValue){
		Key key= new SecretKeySpec(keyValue, ALGORITHM);
		return key;
	}
	public static String encrypt(byte[] keyValue, String data) throws Exception{
		Key key= generateKey(keyValue);
		Cipher cipher= Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE,key);
		byte[] encVal = cipher.doFinal(data.getBytes());
		String encryptedValue= new BASE64Encoder().encode(encVal); 
		return encryptedValue;
	}
	public static String decrypt(byte[] keyValue, String encryptedData) throws Exception{
		
		Key key= generateKey(keyValue);
		Cipher cipher= Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE,key);
		
		
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decValue = cipher.doFinal(decordedValue);
		String decryptedValue= new String(decValue);
		return decryptedValue;
	}
	public static void main(String[] args) throws Exception{
//		byte[] keyValue = "Adkc4gb821vyO0b0".getBytes("UTF-8");
//		String encryptedData = AES.encrypt("Adkc4gb821vyO0b9".getBytes("UTF-8"), "back_url=http%3A%2F%2Fxalo.vn&partner_id=1&msisdn=&method=vtm");
//		System.out.println(URLEncoder.encode(encryptedData,"UTF-8"));
//		String decryptedData = AES.decrypt(keyValue, encryptedData);
//		System.out.println(decryptedData);
//		String decryptedData=AES.decrypt("Adkc4gb821vyO0b9".getBytes("UTF-8"), "/4zbInYClmlGHXyqgR2BfoUsPRJGt8okFwb2Koj4aAg=");
//		System.out.println(decryptedData);
//		encryptedData="og6ZLgy%2FVHlE4e7LZfWIrf2qBawIlCWWHBsOwRF2uSLdS3WRi3fxtyVi5U7vU4vP8AaCh7yufTL0%0A4oexUcCg9g%3D%3D";
//		System.out.println(encryptedData);
//		decryptedData = AES.decrypt(keyValue, encryptedData);
//		System.out.println(decryptedData);
		
		String encryptedData="";
		String decryptedData ="";
		
//		encryptedData="9fy9XgEaGxbWlk0ScuGw0FKnyfcn8KuSXAzIvvDHZ5/Tic6KPA/InFbfIScm6uQeSyMRXEca17mfRB4Pg2j19g==";
//		System.out.println(encryptedData);
//		decryptedData = AES.decrypt("Wrxt32cv7iLM9kaq".getBytes("UTF-8"), encryptedData);
//		System.out.println(decryptedData);
//		System.out.println("--------------------------");
//		
//		encryptedData="9fy9XgEaGxbWlk0ScuGw0D soeiKJDc5/RVaMh3mfmo=";
//		decryptedData = AES.decrypt("Wrxt32cv7iLM9kaq".getBytes("UTF-8"), encryptedData);
//		System.out.println(decryptedData);
//		System.out.println("--------------------------");
		
//		encryptedData="XtUu5+e1rwxZO56ompTOSM0FP6u/9q3qy2r9CSD5sgLa6X0ZuFijH2VvtKzQtipjZw8CBLRuNX7TCz9cVJeD8lImJ9xgZduLaWtrLDyDAQqPSlMTmmKiGC1ZhziNAxtNHukzCgyMefSA2oARvFLdtcAmt+SrtpirsXqyJHORqrvMevs7LpDaBs1PAcKbymdW";
//		System.out.println(encryptedData);
//		decryptedData = AES.decrypt("0123456789abcdef".getBytes("UTF-8"), encryptedData);
//		System.out.println(decryptedData);
//		System.out.println("--------------------------");
		
//		encryptedData="/r0E8D74B+tzOuBVCrhV5iaiK+g1yyEMcPYFGZelu6E=";
//		System.out.println(encryptedData);
//		decryptedData = AES.decrypt("Adkc4gb821vyO0b9".getBytes("UTF-8"), encryptedData);
//		System.out.println(decryptedData);
//		System.out.println("--------------------------");
//		
		
		
//		method=vtm&item_id=123243&item_desc=456&item_price=1200
//		Map<String,String> params= new HashMap<String,String>();
//		params.put(SharedConstants.SERVLET_REQUEST_PARAMS.API_COMMAND_REQ_PARAM, SharedConstants.PAYMENT_API.REQUEST_TRANSACTION_COMMAND);
//		params.put(SharedConstants.SERVLET_REQUEST_PARAMS.METHOD_CHARGING_REQ_PARAM, SharedConstants.CHARGING_METHODS.VMS_WAP_CHARGING);
//		params.put(SharedConstants.SERVLET_REQUEST_PARAMS.ITEM_ID_REQ_PARAM,"MS123456");
//		params.put(SharedConstants.SERVLET_REQUEST_PARAMS.ITEM_DESC_REQ_PARAM,"MS123456 DESC");
//		params.put(SharedConstants.SERVLET_REQUEST_PARAMS.ITEM_PRICE_REQ_PARAM,"1000");
//		System.out.println(MapUtil.mapToQueryString(params));
		
//		String reqInfo = null;
//		reqInfo= AES.encrypt("0123456789abcdef".getBytes("UTF-8"), "http://xalo.vn");
//		System.out.println(reqInfo);
//		
////		params.clear();
////		params.put(SharedConstants.SERVLET_REQUEST_PARAMS.API_COMMAND_REQ_PARAM, SharedConstants.PAYMENT_API.GET_TRANSACTION_STATUS_COMMAND);
////		params.put(SharedConstants.SERVLET_REQUEST_PARAMS.TOKEN_REQ_PARAM, "AAABPdQTNKo.1Urbvu-XXQ__");
////		System.out.println(MapUtil.mapToQueryString(params));
////		//Wrxt32cv7iLM9kaq
////		reqInfo = AES.encrypt("0123456789abcdef".getBytes("UTF-8"), MapUtil.mapToQueryString(params));
////		System.out.println(reqInfo);
		String aaa= AES.encrypt("0123456789abcdef".getBytes("UTF-8"), "hahm");
		System.out.println(aaa);
	}
	
}


