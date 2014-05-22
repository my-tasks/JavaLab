package com.epam.xslt.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epam.xslt.engine.TemplatesManager;
import com.epam.xslt.engine.XmlResourceManager;

import static com.epam.xslt.resources.Constants.*;

public final class XsltRequestUtil {

	private XsltRequestUtil() {
	}

	public static Map<String, String> getParametersMap(
			HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<>();
		Enumeration<String> paramNameList = request.getParameterNames();
		while (paramNameList.hasMoreElements()) {
			String paramName = paramNameList.nextElement();
			String paramValue = request.getParameter(paramName);
			resultMap.put(paramName, paramValue);
		}
		Enumeration<String> attrNameList = request.getAttributeNames();
		while (attrNameList.hasMoreElements()) {
			String attrName = attrNameList.nextElement();
			String attrValue = (String) request.getAttribute(attrName);
			resultMap.put(attrName, attrValue);
		}
		if (resultMap.get(PARAM_SOURCE_ID) == null) {
			resultMap.put(PARAM_SOURCE_ID,
					XmlResourceManager.getDefaultXmlResourceId());
		}
		if (resultMap.get(PARAM_TEMPLATE_ID) == null) {
			resultMap.put(PARAM_TEMPLATE_ID,
					TemplatesManager.getDefaultTemplateId());
		}
		return resultMap;
	}
}
