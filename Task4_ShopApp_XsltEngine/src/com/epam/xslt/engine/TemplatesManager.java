package com.epam.xslt.engine;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.epam.xslt.exception.TemplatesManagerInitiatingException;
import com.epam.xslt.resources.Constants;

public final class TemplatesManager {
	private static final Logger logger = Logger
			.getLogger(TemplatesManager.class);
	private static Map<String, Templates> templatesMap;
	private static String defaultTemplateId;

	private TemplatesManager() {
	}

	public static void init(Map<String, String> xslResourcePathMap,
			String defaultTemplateIdentifier)
			throws TemplatesManagerInitiatingException {
		defaultTemplateId = defaultTemplateIdentifier;
		templatesMap = new ConcurrentHashMap<>();
		TransformerFactory factory = TransformerFactory.newInstance();
		Iterator<Entry<String, String>> it = xslResourcePathMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String id = entry.getKey();
			String path = entry.getValue();
			if (path == null) {
				String errorMessage = "Exception occured when attempting to create a template from given source: \""
						+ path + "\" with id \"" + id + "\"";
				logger.error(errorMessage);
				throw new TemplatesManagerInitiatingException(errorMessage);
			}
			Source xslSource = new StreamSource(path);
			try {
				Templates template = factory.newTemplates(xslSource);
				templatesMap.put(id, template);
				if (logger.isDebugEnabled()) {
					logger.debug("Template with ID \"" + id
							+ "\" is set to TemplatesManager");
				}
			} catch (TransformerConfigurationException | NullPointerException ex) {
				String errorMessage = "Exception occured when attempting to create a template from given source: \""
						+ path + "\"";
				logger.error(errorMessage);
				throw new TemplatesManagerInitiatingException(errorMessage);
			}
		}

	}

	public static Templates getTemplate(String xmlId, String xslId) {
		return getTemplate(xmlId + Constants.ID_SYMBOL + xslId);
	}

	public static Templates getTemplate(String templateId) {
		Templates templates = templatesMap.get(templateId);
		if (templates == null) {
			templates = getGlobalTemplate(templateId);
			if (templates == null) {
				logger.warn("Cannot find appropriate template for \""
						+ templateId + "\"");
			}
		}
		return templates;
	}

	public static Templates getGlobalTemplate(String templateId) {
		Templates template = null;
		try {
			templateId = Constants.GLOBAL + Constants.ID_SYMBOL
					+ templateId.split(Constants.ID_SYMBOL)[1];
			template = templatesMap.get(templateId);
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
			logger.warn("Bad formed templateId \""
					+ templateId
					+ "\". It must meet the requirement to follow the pattern xmlResourceId::xslResourceId");
		}
		return template;
	}

	public static String getDefaultTemplateId() {
		return defaultTemplateId;
	}

}
