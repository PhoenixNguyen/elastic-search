package vn.onepay.web.controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class CaptchaImageGeneratorController extends AbstractController{
	private int height=30;
	private int width=120;
	public static final String CAPTCHA_KEY = "captcha_key_name";
	
	@Override
	protected ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		response.setHeader("Cache-Control", "no-cache");
		  response.setDateHeader("Expires", 0);
		  response.setHeader("Pragma", "no-cache");
		  response.setDateHeader("Max-Age", 0);
			
		  BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR); 
		  Graphics2D graphics2D = image.createGraphics();
		  Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
		  Random r = new Random();
		  String token = Long.toString(Math.abs(r.nextLong()), 36);
		  String ch = token.substring(0,6);
//		  Color c = new Color(0.6662f, 0.4569f, 0.3232f);
		  Color c = new Color(0.3662f, 0.2569f, 0.1232f);
//		  GradientPaint gp = new GradientPaint(30, 30, c, 15, 25, Color.white, true);
		  GradientPaint gp = new GradientPaint(75, 75, Color.white,95, 95, Color.gray, true);
		  graphics2D.setPaint(gp);
		  Font font=new Font("Verdana", Font.CENTER_BASELINE , 26);
		  graphics2D.setFont(font);
		  graphics2D.drawString(ch,2,20);
		  graphics2D.dispose();
		  
		  HttpSession session = request.getSession(true);
		  session.setAttribute(CAPTCHA_KEY,ch);

		  OutputStream outputStream = response.getOutputStream();
		  ImageIO.write(image, "jpeg", outputStream);
		  outputStream.close();
		return null;
	}
}
