package com.epam.xslt.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class LogUtil {
	public static String getRequestParameteresLog(HttpServletRequest request) {
		StringBuilder requestLog = new StringBuilder(
				"Proceesing request with parameters: {");
		Enumeration<String> paramNameList = request.getParameterNames();
		while (paramNameList.hasMoreElements()) {
			String paramName = paramNameList.nextElement();
			String paramValue = request.getParameter(paramName);
			requestLog.append(" " + paramName + "=" + paramValue + " ");
		}
		requestLog.append("}");

		return new String(requestLog);
	}
}
