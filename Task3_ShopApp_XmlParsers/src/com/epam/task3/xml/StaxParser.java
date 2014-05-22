package com.epam.task3.xml;

import java.io.FileInputStream;
import java.util.Collection;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

import com.epam.task3.exception.InvalidDataException;
import com.epam.task3.model.Goods;

public final class StaxParser implements XmlDataParser {
	private static final Logger logger = Logger.getLogger(StaxParser.class);
	private static final StaxParser instance = new StaxParser();

	private StaxParser() {
	}

	protected static StaxParser newInstance() {
		return instance;
	}

	public Collection<Goods> getProductCollection(String filePath,
			Collection<Goods> goods) {
		String currCategory = null;
		String currSubcategory = null;
		Goods currProduct = null;
		try {
			logger.info("Parsing file " + filePath);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory
					.createXMLStreamReader(new FileInputStream(filePath));
			while (reader.hasNext()) {
				int event = reader.next();
				if (event == XMLStreamReader.START_ELEMENT) {
					switch (reader.getLocalName()) {
					case (Constants.CATEGORY):
						currCategory = reader.getAttributeValue(0);
						break;
					case (Constants.SUBCATEGORY):
						currSubcategory = reader.getAttributeValue(0);
						break;
					case (Constants.GOODS):
						currProduct = new Goods();
						currProduct.setName(reader.getAttributeValue(0));
						currProduct.setCategory(currCategory);
						currProduct.setSubcategory(currSubcategory);
						break;
					case (Constants.PRODUCER):
						currProduct.setProducer(reader.getElementText());
						break;
					case (Constants.MODEL):
						currProduct.setModel(reader.getElementText());
						break;
					case (Constants.DATE_OF_ISSUE):
						currProduct.setDateOfIssue(reader.getElementText());
						break;
					case (Constants.COLOR):
						currProduct.setColor(reader.getElementText());
						break;
					case (Constants.PRICE):
						currProduct.setPrice(Long.parseLong(reader
								.getElementText()));
						break;
					case (Constants.NOT_IN_STOCK):
						currProduct.setNotInStock(Boolean.parseBoolean(reader
								.getElementText()));
						break;
					}
				}
				if (event == XMLStreamReader.END_ELEMENT) {
					switch (reader.getLocalName()) {
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
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Exception occured when parsing file " + filePath
					+ ". ", ex);
			throw new InvalidDataException(
					"Invalid xml data were detected. The data does not meet all the requirements");
		}
		return goods;
	}
}
