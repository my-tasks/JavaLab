package com.epam.xslt.util;

import static com.epam.xslt.resources.Constants.KEY_FOR_SAFE_REQUEST;
import static com.epam.xslt.resources.Constants.PARAM_SAFE_REQUEST;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class SafeRequestUtil {
	private static final Logger logger = Logger
			.getLogger(SafeRequestUtil.class);

	public static boolean isSafeRequest(HttpServletRequest request) {
		boolean safeRequest = Boolean.valueOf(request
				.getParameter(PARAM_SAFE_REQUEST));
		return safeRequest;
	}

	public static boolean isSafetyKeyAlreadySet(HttpServletRequest request) {
		String key = request.getParameter(KEY_FOR_SAFE_REQUEST);
		if (key == null) {
			return false;
		}
		if (key.isEmpty()) {
			return false;
		}
		return true;
	}

	public static void setSafetyKey(HttpServletRequest request) {
		String token = Tokenizer.generateToken();
		request.setAttribute(KEY_FOR_SAFE_REQUEST, token);
		request.getSession().setAttribute(KEY_FOR_SAFE_REQUEST, token);
		if (logger.isDebugEnabled()) {
			logger.debug("Safety key " + token
					+ " is set to the request and session scopes.");
		}
	}

	public static boolean isValidRequest(HttpServletRequest request) {
		String requestTokenKey = request.getParameter(KEY_FOR_SAFE_REQUEST);
		if (requestTokenKey == null || requestTokenKey.isEmpty()) {
			requestTokenKey = (String) request
					.getAttribute(KEY_FOR_SAFE_REQUEST);
		}
		if (requestTokenKey != null) {
			String sessionTokenKey = (String) request.getSession()
					.getAttribute(KEY_FOR_SAFE_REQUEST);
			if (sessionTokenKey == null
					|| !requestTokenKey.equals(sessionTokenKey)) {
				logger.debug("The request doesnt meets the safety requirements.");
				return false;
			}
		} else {
			logger.debug("Safety key is not set. [Default mode consider this as the request meets the safety requirements.]");
		}
		logger.debug("The request meets the safety requirements.");
		return true;
	}

	public static void resetSessionSafetyKey(HttpServletRequest request) {
		request.getSession().removeAttribute(KEY_FOR_SAFE_REQUEST);
		logger.debug("The safety key is removed from the session scope");
	}
}
