package com.epam.task3.xml;

import java.util.Collection;

import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.task3.exception.InvalidDataException;
import com.epam.task3.model.Goods;

public final class SaxParser implements XmlDataParser {
	private static final Logger logger = Logger.getLogger(SaxParser.class);
	private static final SaxParser instance = new SaxParser();

	private SaxParser() {
	}

	protected static SaxParser newInstance() {
		return instance;
	}

	public Collection<Goods> getProductCollection(String filePath,
			Collection<Goods> emptyCollection) {
		try {
			logger.info("Parsing file " + filePath);
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			SaxHandler handler = new SaxHandler(emptyCollection);
			reader.setContentHandler(handler);
			reader.parse(new InputSource(filePath));
			return handler.getGoods();
		} catch (Exception ex) {
			logger.error(
					"Exception occured when attempting to parse file xml: "
							+ filePath, ex);
			throw new InvalidDataException(
					"Invalid xml data were detected. The data does not meet all the requirements");
		}
	}

	private class SaxHandler extends DefaultHandler {
		private Collection<Goods> goods;
		private String currCategory = null;
		private String currSubcategory = null;
		private Goods currProduct = null;;
		private String productParam = "";

		public SaxHandler(Collection<Goods> emptyCollection) {
			goods = emptyCollection;
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			switch (localName) {
			case (Constants.CATEGORY):
				currCategory = attributes.getValue(Constants.NAME);
				break;
			case (Constants.SUBCATEGORY):
				currSubcategory = attributes.getValue(Constants.NAME);
				break;
			case (Constants.GOODS):
				currProduct = new Goods();
				currProduct.setName(attributes.getValue(Constants.NAME));
				currProduct.setCategory(currCategory);
				currProduct.setSubcategory(currSubcategory);
				break;
			default:
				productParam = localName;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			switch (productParam) {
			case (Constants.PRODUCER):
				currProduct.setProducer(new String(ch, start, length));
				break;
			case (Constants.MODEL):
				currProduct.setModel(new String(ch, start, length));
				break;
			case (Constants.DATE_OF_ISSUE):
				currProduct.setDateOfIssue(new String(ch, start, length));
				break;
			case (Constants.COLOR):
				currProduct.setColor(new String(ch, start, length));
				break;
			case (Constants.PRICE):
				currProduct.setPrice(Long.parseLong(new String(ch, start,
						length)));
				break;
			case (Constants.NOT_IN_STOCK):
				currProduct.setNotInStock(Boolean.parseBoolean(new String(ch,
						start, length)));
				break;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			switch (localName) {
			case (Constants.CATEGORY):
				currCategory = null;
				break;
			case (Constants.SUBCATEGORY):
				currSubcategory = null;
				break;
			case (Constants.GOODS):
				goods.add(currProduct);
				currProduct = null;
				break;
			default:
				productParam = "";
			}
		}

		public Collection<Goods> getGoods() {
			return goods;
		}
	}
}
