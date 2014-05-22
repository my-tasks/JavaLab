package com.epam.task3.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.task3.exception.InvalidDataException;
import com.epam.task3.util.HtmlResponseGenerator;
import com.epam.task3.xml.Constants;
import com.epam.task3.xml.ParserFactory;
import com.epam.task3.xml.XmlDataParser;
import com.epam.task3.xml.XmlDataValidator;
import com.epam.task3.xml.XmlErrorHandler;

public final class XmlParseAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String realPath;

	@Override
	public void init() throws ServletException {
		super.init();
		realPath = getServletContext().getRealPath("/");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String xmlFilePath = realPath + Constants.GOODS_XML_FILE;
			String xsdFilePath = realPath + Constants.GOODS_XSD_FILE;
			XmlErrorHandler errors = XmlDataValidator.validate(xmlFilePath,
					xsdFilePath);
			if (errors.isEmpty()) {
				String parserType = request.getParameter("parser");
				XmlDataParser parser = ParserFactory.getParser(parserType);
				Writer out = response.getWriter();
				HtmlResponseGenerator.generateResponse(parser, xmlFilePath, out);
			} else {
				throw new InvalidDataException(errors.getErrors());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("file", Constants.GOODS_XML_FILE);
			if (ex instanceof InvalidDataException) {
				request.setAttribute("errors",
						((InvalidDataException) ex).getErrors());
			} else {
				request.setAttribute("errors", ex.getMessage());
			}
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		}
	}
}
