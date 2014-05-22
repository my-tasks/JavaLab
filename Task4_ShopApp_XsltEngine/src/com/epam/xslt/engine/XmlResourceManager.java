package com.epam.xslt.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.epam.xslt.exception.XmlResourceManagerInitiatingException;

public final class XmlResourceManager {
	private static final Logger logger = Logger
			.getLogger(XmlResourceManager.class);
	private static Map<String, String> xmlResourcePathMap;
	private static String defaultXmlResourceId;

	private XmlResourceManager() {
	}

	public static void init(Map<String, String> pathMap,
			String defaultXmlResourceIdentifier) {
		defaultXmlResourceId = defaultXmlResourceIdentifier;
		xmlResourcePathMap = pathMap;
		String id = null;
		String path = null;
		Iterator<Entry<String, String>> it = xmlResourcePathMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			id = entry.getKey();
			path = entry.getValue();
			if (!isValidPath(path)) {
				String errorMessage = String.format("Resource with ID \"" + id
						+ "\" is not found by path: "+path);
				logger.error(errorMessage);
				throw new XmlResourceManagerInitiatingException(errorMessage);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Xml resource with ID \"" + id
							+ "\" is set to XmlResourceManager.");
				}
			}
		}
	}

	public static String getPath(String xmlSourceId) {
		return xmlResourcePathMap.get(xmlSourceId);
	}

	public static StreamSource getXmlStreamSource(String xmlSourceId) {
		return new StreamSource(xmlResourcePathMap.get(xmlSourceId));
	}

	public static StreamResult getXmlStreamResult(String xmlSourceId) {
		return new StreamResult(xmlResourcePathMap.get(xmlSourceId));
	}

	public static FileOutputStream getXmlFileOutputStream(String xmlTargetId)
			throws FileNotFoundException {
		return new FileOutputStream(xmlResourcePathMap.get(xmlTargetId));
	}

	public static FileInputStream getXmlFileInputStream(String xmlTargetId)
			throws FileNotFoundException {
		return new FileInputStream(xmlResourcePathMap.get(xmlTargetId));
	}

	private static boolean isValidPath(String path) {
		if (new File(path).exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static String getDefaultXmlResourceId() {
		return defaultXmlResourceId;
	}

}
