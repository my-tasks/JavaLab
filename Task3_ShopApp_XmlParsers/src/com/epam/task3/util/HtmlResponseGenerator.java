package com.epam.task3.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.epam.task3.model.Goods;
import com.epam.task3.xml.XmlDataParser;

public final class HtmlResponseGenerator {

	private static final String HEADER = "<HTML><HEAD><META charset='UTF-8'><LINK rel='stylesheet' type='text/css' href='./resources/style.css'/> "
			+ "</HEAD><BODY><DIV class='centered'>";
	private static final String HEAD_MENU = "<TABLE><TR><TD><H2><A href='./index.html'>Back to main page</A></H2></TD><TD class='menu'><H2><A href='./parse?parser=dom'>DOM</A>"
			+ "&emsp; | &emsp;<A href='./parse?parser=sax'>SAX</A>&emsp; | &emsp;<A href='./parse?parser=stax'>STAX</A></H2></TD></TR></TABLE><BR><HR><BR>";
	private static final String TABLE_HEAD = "<TABLE>";
	private static final String CATEGORY_TABLE_ROW = "<TR><TD colspan='7' class='7x'><H2>%s</H2></TD></TR>";
	private static final String SUBCATEGORY_TABLE_ROW = "<TR><TD colspan='7' class='7x'><H3>%s</H3></TD></TR>";
	private static final String PRODUCT_TABLE_ROW = "<TR><TD></TD><TD title='Product Name' >%s</TD><TD title='Provider'>%s</TD><TD title='Model'>%s</TD>"
			+ "<TD title='Date of issue'>%s</TD><TD title='Color'><DIV id='square' style='background: %s'/></TD><TD title='Price'>%s</TD></TR>";
	private static final String NOT_IN_STOCK = "<DIV title='Not In Stock' class='not-in-stock'>NOT IN STOCK</DIV>";
	private static final String TABLE_FOOT = "</TABLE>";
	private static final String ERROR_TABLE_ROW = "<TR><TD></TD><TD colspan='6' class='error'>INVALID DATA</TD></TR>";
	private static final String FOOTER = "</DIV></BODY></HTML>";

	private HtmlResponseGenerator(String parserType) {
	}

	public static String getCategoryTableRow(String categoryName) {
		return String.format(CATEGORY_TABLE_ROW, categoryName);
	}

	public static String getSubcategoryTableRow(String subcategoryName) {
		return String.format(SUBCATEGORY_TABLE_ROW, subcategoryName);
	}

	public static String getProductTableRow(Goods product) {
		String result = String.format(PRODUCT_TABLE_ROW, product.getName(),
				product.getProducer(), product.getModel(),
				product.getDateOfIssue(), product.getColor(),
				product.isNotInStock() ? NOT_IN_STOCK : product.getPrice()
						+ " $");
		return result;
	}

	public static void generateResponse(XmlDataParser parser, String filePath,
			Writer out) throws IOException {
		out.write(HEADER + HEAD_MENU);
		out.write(TABLE_HEAD);
		String currCategory = "";
		String currSubcategory = "";
		Collection<Goods> goods = parser.getProductCollection(filePath,
				new LinkedList<Goods>());
		Iterator<Goods> iterator = goods.iterator();
		while (iterator.hasNext()) {
			Goods product = iterator.next();
			if (!currCategory.equals(product.getCategory())) {
				currCategory = product.getCategory();
				out.write(getCategoryTableRow(currCategory));
				currSubcategory = product.getSubcategory();
				out.write(getSubcategoryTableRow(currSubcategory));
			}
			if (!currSubcategory.equals(product.getSubcategory())) {
				currSubcategory = product.getSubcategory();
				out.write(getSubcategoryTableRow(currSubcategory));
			}
			out.write(GoodsUtil.validate(product) ? getProductTableRow(product)
					: ERROR_TABLE_ROW);
		}
		out.write(TABLE_FOOT);
		out.write(FOOTER);
		out.flush();
		out.close();
	}
}
