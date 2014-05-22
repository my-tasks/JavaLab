package com.epam.shopapp.util;

import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlDataLoader {
	public static void main(String[] args) throws JDOMException, IOException {
		read("src/goods.xml");
	}

	public static void read(String file) throws JDOMException, IOException {
		Element categories = ((Document) (new SAXBuilder().build(file)))
				.getRootElement();
		System.out.println(categories.getName());
	}
}
