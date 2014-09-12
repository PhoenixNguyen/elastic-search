package vn.onepay.otp;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Clock;

public class Test {
	private Clock clock;
    private Totp totp;
    private String sharedSecret = "B2374TNIQ3HKC446";
    Test(){
    	totp = new Totp(sharedSecret, clock);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Clock clock = new Clock(5);
		String secret = "hahm";

        Totp totp = new Totp(secret, clock);
        String otp = totp.now();
		System.out.println(otp);
		System.out.println(totp.verify(otp));
	}

}
