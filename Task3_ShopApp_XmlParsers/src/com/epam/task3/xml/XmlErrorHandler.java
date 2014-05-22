package com.epam.task3.xml;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public final class XmlErrorHandler extends DefaultHandler {
	private List<String> errors = new ArrayList<>();

	@Override
	public void warning(SAXParseException e) {
		errors.add("WARN: " + getLineAdress(e) + " - " + e.getMessage());
	}

	@Override
	public void error(SAXParseException e) {
		errors.add("ERROR: " + getLineAdress(e) + " - " + e.getMessage());
	}

	@Override
	public void fatalError(SAXParseException e) {
		errors.add("FATAL: " + getLineAdress(e) + " - " + e.getMessage());
	}

	private String getLineAdress(SAXParseException e) {
		return "row "+e.getLineNumber() + " : col " + e.getColumnNumber();
	}

	public List<String> getErrors() {
		return errors;
	}

	public boolean isEmpty() {
		return errors.size() == 0;
	}

	public int getErrorsAmount() {
		return errors.size();
	}

}
