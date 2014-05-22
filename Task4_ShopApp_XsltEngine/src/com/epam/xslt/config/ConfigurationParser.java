package com.epam.xslt.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.xslt.exception.ConfigurationException;

import static com.epam.xslt.resources.Constants.*;

public final class ConfigurationParser {
	private static final Logger logger = Logger
			.getLogger(ConfigurationParser.class);
	private Map<String, String> xmlResourcePathMap;
	private Map<String, String> xslResourcePathMap;
	private Set<String> xmlResourceIdSet;
	private String defaultXmlResourceId;
	private String defaultTemplateId;
	private Map<String, List<String>> formConstraints;

	public void parseConfigurationFile(String filePath, String contextPath)
			throws ConfigurationException {
		try {
			SaxHandler handler = new SaxHandler(contextPath);
			logger.info("Parsing configuration file " + filePath);
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setContentHandler(handler);
			reader.parse(new InputSource(filePath));
			xmlResourcePathMap = handler.xmlResourcePathMap;
			xslResourcePathMap = handler.xslResourcePathMap;
			xmlResourceIdSet = handler.xmlResourceIdSet;
			defaultXmlResourceId = handler.defaultXmlResourceId;
			defaultTemplateId = handler.defaultTemplateId;
			formConstraints = handler.formConstraints;
		} catch (ConfigurationException ex) {
			logger.error(
					"Exception occured when parsing configuration file xml: "
							+ filePath, ex);
			throw ex;
		} catch (Exception ex) {
			logger.error(
					"Exception occured when parsing configuration file xml: "
							+ filePath, ex);
		}
	}

	public Map<String, String> getXmlResourcePathMap() {
		return xmlResourcePathMap;
	}

	public Map<String, String> getXslResourcePathMap() {
		return xslResourcePathMap;
	}

	public Set<String> getXmlResourceIdSet() {
		return xmlResourceIdSet;
	}

	public String getDefaultXmlResourceId() {
		return defaultXmlResourceId;
	}

	public String getDefaultTemplateId() {
		return defaultTemplateId;
	}

	public Map<String, List<String>> getFormConstraints() {
		return formConstraints;
	}

	private class SaxHandler extends DefaultHandler {
		private String contextPath;
		private Map<String, String> globalLocations;
		private Map<String, String> xslResourcePathMap;
		private Map<String, String> xmlResourcePathMap;
		private Set<String> xmlResourceIdSet;
		private String xmlResourceId;
		private String xslResourceId;
		private String resourceName;
		private String resourcePath;
		private String groupLocation;
		private String location;
		private String locationId;
		private String defaultXmlResourceId;
		private String defaultTemplateId;
		private Map<String, List<String>> formConstraints;
		private String enumKey;
		private LinkedList<String> enumValuesList;
		private boolean value;

		private SaxHandler(String contextPath) {
			this.contextPath = contextPath;
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			switch (localName) {
			case (TAG_ROOT):
				globalLocations = new HashMap<>();
				xslResourcePathMap = new HashMap<>();
				xmlResourcePathMap = new HashMap<>();
				xmlResourceIdSet = new HashSet<>();
				break;
			case (TAG_LOCATION):
				locationId = attributes.getValue(ATTR_ID);
				location = attributes.getValue(ATTR_PATH);
				if (locationId == null && !globalLocations.containsKey(DEFAULT)) {
					globalLocations.put(DEFAULT, location);
				} else {
					globalLocations.put(locationId, location);
				}
				break;
			case (TAG_GLOBAL_XSL_SOURCES):
				xmlResourceId = GLOBAL;
				break;
			case (TAG_XSL_SOURCE):
				xslResourceId = xmlResourceId + ID_SYMBOL
						+ attributes.getValue(ATTR_ID);
				resourceName = attributes.getValue(ATTR_RESOURCE_NAME);
				location = attributes.getValue(ATTR_LOCATION);
				if (location == null) {
					locationId = attributes.getValue(ATTR_GLOBAL_LOCATION);
					if (locationId != null) {
						location = globalLocations.get(locationId);
					} else {
						location = (groupLocation != null) ? groupLocation
								: globalLocations.get(DEFAULT);
					}
				}
				resourcePath = contextPath + location + resourceName;
				xslResourcePathMap.put(xslResourceId, resourcePath);
				break;
			case (TAG_XML_SOURCE):
				xmlResourceId = attributes.getValue(ATTR_ID);
				resourceName = attributes.getValue(ATTR_RESOURCE_NAME);
				location = attributes.getValue(ATTR_LOCATION);
				if (location == null) {
					groupLocation = attributes.getValue(ATTR_GROUP_LOCATION);
					location = groupLocation;
					if (location == null) {
						locationId = attributes.getValue(ATTR_GLOBAL_LOCATION);
						location = globalLocations.get(locationId);
						if (location == null) {
							location = globalLocations.get(DEFAULT);
						}
					}
				}
				resourcePath = contextPath + location + resourceName;
				xmlResourcePathMap.put(xmlResourceId, resourcePath);
				xmlResourceIdSet.add(xmlResourceId);
				break;
			case (TAG_DEFAULT_XSOURCE):
				// setting default xml resource
				defaultXmlResourceId = attributes.getValue(ATTR_XML_SOURCE_ID);
				// setting default xsl resource
				defaultTemplateId = attributes.getValue(ATTR_XSL_SOURCE_ID);
				resourcePath = xslResourcePathMap.get(defaultXmlResourceId
						+ ID_SYMBOL + defaultTemplateId);
				if (resourcePath == null) {
					resourcePath = xslResourcePathMap.get(GLOBAL + ID_SYMBOL
							+ defaultTemplateId);
					if (resourcePath == null) {
						String errorMessage = "Cannot define path to xsl source with id \""
								+ defaultTemplateId + "\". ";
						logger.error(errorMessage);
						throw new ConfigurationException(errorMessage);
					}
				}
				break;
			case (TAG_FORM_CONSTRAINTS):
				formConstraints = new HashMap<>();
				break;
			case (TAG_PATTERN):
				String patternKey = attributes.getValue(ATTR_KEY);
				String patternValue = attributes.getValue(ATTR_VALUE);
				List<String> pattern = new LinkedList<String>();
				pattern.add(patternValue);
				formConstraints.put(patternKey, pattern);
				break;
			case (TAG_ENUM):
				enumValuesList = new LinkedList<>();
				enumKey = attributes.getValue(ATTR_KEY);
				String enumValue = attributes.getValue(ATTR_VALUES);
				if (enumValue!=null&&!enumValue.trim().isEmpty()) {
					String[] values = enumValue.split(",");
					for (String value : values) {
						if (!value.trim().isEmpty()) {
							enumValuesList.add(value.trim());
						}
					}
				}
				break;
			case (TAG_VALUE):
				value = true;
				break;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (value) {
				enumValuesList.add(new String(ch, start, length).trim());
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			switch (localName) {
			case (TAG_GLOBAL_SOURCE_LOCATIONS):
				if (globalLocations.get(DEFAULT) == null) {
					globalLocations.put(DEFAULT, "");
				}
				break;
			case (TAG_XML_SOURCE):
				groupLocation = null;
				break;
			case (TAG_ENUM):
				formConstraints.put(enumKey, new LinkedList(enumValuesList));
				enumValuesList.clear();
				enumKey = null;
				break;
			case (TAG_VALUE):
				value = false;
				break;
			}
		}
	}
}
