package com.epam.task3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.epam.task3.xml.Constants;

public class XslTransformAction extends HttpServlet {
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

	public void process(HttpServletRequest request, HttpServletResponse response) {
		String currCategory = request.getParameter("category");
		String currSubcategory = request.getParameter("subcategory");
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(new StreamSource(
					realPath + Constants.XSL));
			if (currCategory != null && !"".equals(currCategory)) {
				System.out.println("setting category " + currCategory);
				transformer.setParameter("currCategory", currCategory);
				if (currSubcategory != null && !"".equals(currSubcategory)) {
					System.out
							.println("setting subcategory " + currSubcategory);
					transformer
							.setParameter("currSubcategory", currSubcategory);
				}
			}

			transformer.transform(new StreamSource(realPath
					+ Constants.GOODS_XML_FILE),
					new StreamResult(response.getWriter()));
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (TransformerConfigurationException ex) {
			System.out.println("blablabla");
			ex.printStackTrace();
		} catch (TransformerException ex) {
			ex.printStackTrace();
		}
	}

}
