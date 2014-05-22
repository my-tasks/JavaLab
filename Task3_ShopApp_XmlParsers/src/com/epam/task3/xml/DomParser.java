package com.epam.task3.xml;

import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.task3.exception.InvalidDataException;
import com.epam.task3.model.Goods;

public final class DomParser implements XmlDataParser {
	private static final Logger logger = Logger.getLogger(DomParser.class);
	private static final DomParser instance = new DomParser();

	private DomParser() {
	}

	protected static DomParser newInstance() {
		return instance;
	}

	public Collection<Goods> getProductCollection(String filePath,
			Collection<Goods> goods) {
		try {
			logger.info("Parsing file " + filePath);
			Document shopDoc = loadDocument(filePath);
			NodeList goodsList = shopDoc.getElementsByTagNameNS(
					Constants.NS_URI, Constants.GOODS);
			// NodeList goodsList = shopDoc.getElementsByTagName("shop:goods");
			int length = goodsList.getLength();
			for (int i = 0; i < length; i++) {
				Node goodsNode = goodsList.item(i);
				if (goodsNode instanceof Element) {
					goods.add(parseGoodsNode(goodsNode));
				}
			}
		} catch (Exception ex) {
			logger.error(
					"Exception occured when attempting to parse file xml: "
							+ filePath, ex);
			throw new InvalidDataException(
					"Invalid xml data were detected. The data does not meet all the requirements");
		}
		return goods;
	}

	private Goods parseGoodsNode(Node productNode) {
		Goods goods = new Goods();
		String category = ((Element) productNode.getParentNode()
				.getParentNode()).getAttribute(Constants.NAME);
		String subcategory = ((Element) productNode.getParentNode())
				.getAttribute(Constants.NAME);
		String name = ((Element) productNode).getAttribute(Constants.NAME);
		goods.setCategory(category);
		goods.setSubcategory(subcategory);
		goods.setName(name);
		NodeList goodsParams = productNode.getChildNodes();
		int length = goodsParams.getLength();
		for (int i = 0; i < length; i++) {
			Node param = goodsParams.item(i);
			String paramName = param.getLocalName();
			if (paramName != null) {
				switch (paramName) {
				case (Constants.PRODUCER):
					goods.setProducer(param.getTextContent());
					break;
				case (Constants.MODEL):
					goods.setModel(param.getTextContent());
					break;
				case (Constants.DATE_OF_ISSUE):
					goods.setDateOfIssue(param.getTextContent());
					break;
				case (Constants.COLOR):
					goods.setColor(param.getTextContent());
					break;
				case (Constants.PRICE):
					goods.setPrice(Long.parseLong(param.getTextContent()));
					break;
				case (Constants.NOT_IN_STOCK):
					goods.setNotInStock(Boolean.parseBoolean(param
							.getTextContent()));
					break;
				}
			}
		}
		return goods;
	}

	private Document loadDocument(String filePath)
			throws ParserConfigurationException, SAXException, IOException {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(filePath);
		doc.getDocumentElement().normalize();
		return doc;
	}
}
