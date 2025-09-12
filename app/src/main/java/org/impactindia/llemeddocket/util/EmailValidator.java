package org.impactindia.llemeddocket.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/**
	 * Validate hex with regular expression
	 * 
	 * @param email
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	
	public static boolean validate(String email) {
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
			
}
