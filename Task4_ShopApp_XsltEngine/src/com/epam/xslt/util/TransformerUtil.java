package com.epam.xslt.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;

public class TransformerUtil {

	public static Transformer getParameterizedTransformer(Templates template,
			Map<String, String> params)
			throws TransformerConfigurationException {
		Transformer transformer = null;
		transformer = template.newTransformer();
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			transformer.setParameter(key, value);
		}
		return transformer;
	}
}
