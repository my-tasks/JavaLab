package com.epam.xslt.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public final class FormValidator {
	private static final Logger logger = Logger.getLogger(FormValidator.class);
	private static Map<String, List<String>> formConstraints;
	private static HashMap<Thread, Boolean> validityMap = new HashMap<>();

	private FormValidator() {
	}

	public static void init(Map<String, List<String>> constraints) {
		formConstraints = new HashMap<>();
		Iterator<Entry<String, List<String>>> it = constraints.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, List<String>> entry = it.next();
			String key = entry.getKey();
			List<String> constraint = entry.getValue();
			formConstraints.put(key, constraint);
			if (logger.isDebugEnabled()) {
				logger.debug("Constraint \"" + key + "\"=" + constraint
						+ " is set to FormValidator");
			}
		}
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

	public static boolean isValidByPattern(String patternKey, String token) {
		String patternValue = null;
		patternValue = formConstraints.get(patternKey).get(0);
		Pattern pattern = Pattern.compile(patternValue);
		Matcher matcher = pattern.matcher(token.trim());
		return matcher.matches();
	}

	public static boolean isValidEnumValue(String enumId, String fieldValue) {
		List<String> enumValues = null;
		enumValues = formConstraints.get(enumId);
		Iterator<String> it = enumValues.iterator();
		while (it.hasNext()) {
			String a = it.next();
			if (a.equals(fieldValue)) {
				return true;
			}
		}
		return false;
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
}
