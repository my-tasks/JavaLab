package com.epam.task3.xml;

import org.apache.log4j.Logger;

public final class ParserFactory {
	private static final Logger logger = Logger.getLogger(ParserFactory.class);

	private ParserFactory() {
	}

	public static XmlDataParser getParser(String type) {
		if (type == null) {
			logger.info("Default parser type is set: SaxParser");
			return SaxParser.newInstance();
		}
		switch (type.toLowerCase()) {
		case (Constants.DOM):
			logger.info("DomParser is created");
			return DomParser.newInstance();
		case (Constants.SAX):
			logger.info("SaxParser is created");
			return SaxParser.newInstance();
		case (Constants.STAX):
			logger.info("STaxParser is created");
			return StaxParser.newInstance();
		default:
			logger.info("Default parser type is set: SaxParser");
			return SaxParser.newInstance();
		}
	}
}
