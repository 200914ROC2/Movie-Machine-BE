package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;


import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import java.util.Optional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class Utility {

	private static final SecureRandom RAND = new SecureRandom();
	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 512;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	private static final String salt = "DARMFcJcJDeNMmNMLkZN4rSnHV2OQPDd27yi5fYQ77r2vKTa";

	
	public static HashMap<String,String> getJsonFromRequest(HttpServletRequest req){
		
		HashMap<String,String> params = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null; 
		try {
			reader = req.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		JsonParser parser = Json.createParser(new StringReader(sb.toString()));
		String currentKey = "";
		while (parser.hasNext()) {		
			
			final Event event = parser.next();
			switch (event) {
			case KEY_NAME:				
				params.put(parser.getString(), "");
				currentKey = parser.getString();
				break;
			case VALUE_STRING:				
				params.put(currentKey, parser.getString());
				break;
			}	
		}
		parser.close(); 
		return params;
		
	}
	
	public static void PrintJson(HttpServletResponse resp, String message) {
		JSONObject obj = new JSONObject();		
		obj.put("message", message);
		obj.toString();
		PrintWriter out;
		try {
			out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(obj);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * How to Securely Store a Password in Java
	 * https://dev.to/awwsmm/how-to-encrypt-a-password-in-java-42dh
	 */

	public static Optional<String> generateSalt(final int length) {

		if (length < 1) {
			System.err.println("error in generateSalt: length must be > 0");
			return Optional.empty();
		}

		byte[] salt = new byte[length];
		RAND.nextBytes(salt);

		return Optional.of(Base64.getEncoder().encodeToString(salt));
	}

	public static Optional<String> hashPassword(String password) {

		char[] chars = password.toCharArray();
		byte[] bytes = salt.getBytes();

		PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

		Arrays.fill(chars, Character.MIN_VALUE);

		try {
			SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] securePassword = fac.generateSecret(spec).getEncoded();
			return Optional.of(Base64.getEncoder().encodeToString(securePassword));

		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			System.err.println("Exception encountered in hashPassword()");
			return Optional.empty();

		} finally {
			spec.clearPassword();
		}
	}

	public static boolean verifyPassword(String password, String key) {
		Optional<String> optEncrypted = hashPassword(password);
		if (!optEncrypted.isPresent())
			return false;
		return optEncrypted.get().equals(key);
	}

	/*
	 * Random Token Generator
	 */

	public static String getRandomToken() {
		return getRandomString(10);
	}

	static String getRandomString(int n) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	public static Timestamp getTimestamp() {
		Date date = new Date();
		long time = date.getTime();
		return new Timestamp(time);
	}

	public static boolean checkNumber(String s) {
		boolean numValid = false;
		try {
			Integer.parseInt(s);			
			numValid = true;
		} catch (NumberFormatException e) { 
			
		}
		return numValid;
	}
	
	public static String getStrFromPath(String path)
	{
		StringBuilder uri_sb = new StringBuilder(path);  
		if (uri_sb.indexOf("/") != -1)		
			uri_sb.replace(uri_sb.indexOf("/"), uri_sb.length(), ""); 		
		else
			return path; 
		return uri_sb.toString();  
	}
	
	public static int getParamsNumbers(String path)
	{
		StringBuilder uri_sb = new StringBuilder(path);  
		if (uri_sb.indexOf("/") != -1)		
			uri_sb.replace(0, uri_sb.indexOf("/")+1, ""); 		
		if(checkNumber(uri_sb.toString()))
			return Integer.parseInt(uri_sb.toString());
		else 
			return -1;   			
	}
	
	public static double round(double value, int places) { 
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP); 
	    return bd.doubleValue();
	}

}
