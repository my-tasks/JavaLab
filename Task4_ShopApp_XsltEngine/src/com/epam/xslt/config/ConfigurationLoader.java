package com.epam.xslt.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.epam.xslt.engine.TemplatesManager;
import com.epam.xslt.engine.TransformExecutor;
import com.epam.xslt.engine.XmlResourceManager;
import com.epam.xslt.exception.ConfigurationException;
import com.epam.xslt.util.FormValidator;

public class ConfigurationLoader {
	private static final Logger logger = Logger
			.getLogger(ConfigurationLoader.class);

	public static void loadConfiguration(String configFilePath,
			String contextPath) throws ConfigurationException {
		try {
			// parsing configuration xml file - getting maps of pathes to xml
			// and xsl files
			ConfigurationParser parser = new ConfigurationParser();
			parser.parseConfigurationFile(configFilePath, contextPath);

			// initializing XmlResourceManager:
			Map<String, String> xmlResourcePathMap = parser
					.getXmlResourcePathMap();
			String defaultXmlResourceId = parser.getDefaultXmlResourceId();
			XmlResourceManager.init(xmlResourcePathMap, defaultXmlResourceId);

			// initializing TemplatesManager:
			Map<String, String> xslResourcePathMap = parser
					.getXslResourcePathMap();
			Set<String> xmlResourceIdSet = parser.getXmlResourceIdSet();
			String defaultTemplateId = parser.getDefaultTemplateId();
			TemplatesManager.init(xslResourcePathMap, defaultTemplateId);

			// initializing TransformExecutor
			TransformExecutor.init(xmlResourceIdSet);

			// initializing FormValidator
			Map<String, List<String>> constraints = parser.getFormConstraints();
			FormValidator.init(constraints);
		} catch (ConfigurationException ex) {
			logger.error("Configuration loading failed. ", ex);
			throw new ConfigurationException("Configuration loading failed. ",
					ex);
		}

	}
}
