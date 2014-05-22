package com.epam.task3.xml;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public final class XmlDataValidator {
	public static XmlErrorHandler validate(String filePath, String schemaPath) {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		XmlErrorHandler errors = new XmlErrorHandler();
		try {
			Schema schema = factory.newSchema(new File(schemaPath));
			Validator validator = schema.newValidator();
			Source source = new StreamSource(filePath);
			validator.setErrorHandler(errors);
			validator.validate(source);
		} catch (Exception ex) {
			errors.getErrors().add(
					"ERROR: error occured when validating file " + filePath
							+ " with schema " + schemaPath + ": "
							+ ex.getMessage());
		}
		return errors;
	}
}
