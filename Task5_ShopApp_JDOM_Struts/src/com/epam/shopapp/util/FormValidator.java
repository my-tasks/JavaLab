package com.epam.shopapp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class FormValidator {
	private static final Logger logger = Logger.getLogger(FormValidator.class);
	private static HashMap<Thread, Boolean> validityMap = new HashMap<>();
	private static final String DATE_PATTERN = "(((0)[1-9]|[1,2][0-9]|(3)[0-1])-((0)[1,3,5,7,8]|(1)[0,2])-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9]|(30))-((0)[4,6,9]|(11))-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9])-(02)-((20)[0-9][0-9]))";
	private static final String MODEL_PATTERN = "[\u0430-\u044F\u0410-\u044Fa-zA-Z][\u0430-\u044F\u0410-\u044Fa-zA-Z][0-9][0-9][0-9]";
	private static final String PRICE_PATTERN = "[0-9]+";

	private FormValidator() {
	}

	public static boolean isEmptyFieldValue(String field) {
		if (field == null || field.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmptyFieldValue(String field) {
		if (field == null || field.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static boolean isCheckboxChecked(String notInStock) {
		if ("on".equals(notInStock)) {
			return true;
		}
		return false;
	}

	public static void setRequestValidity(Boolean isValid) {
		Thread currThread = Thread.currentThread();
		validityMap.put(currThread, isValid);
	}

	public static boolean isValidRequest() {
		Thread currThread = Thread.currentThread();
		boolean result = (validityMap.get(currThread) != null) ? validityMap
				.get(currThread) : true;
		validityMap.remove(currThread);
		return result;
	}

	public static boolean isValidDate(String token) {
		Pattern pattern = Pattern.compile(DATE_PATTERN);
		Matcher matcher = pattern.matcher(token.trim());
		return matcher.matches();
	}

	public static boolean isValidModel(String token) {
		Pattern pattern = Pattern.compile(MODEL_PATTERN);
		Matcher matcher = pattern.matcher(token.trim());
		return matcher.matches();
	}

	public static boolean isValidPrice(String token) {
		Pattern pattern = Pattern.compile(PRICE_PATTERN);
		Matcher matcher = pattern.matcher(token.trim());
		return matcher.matches();
	}
}
