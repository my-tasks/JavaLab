package com.epam.task3.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.task3.model.Goods;
import com.epam.task3.xml.Constants;

public final class GoodsUtil {

	public static boolean validate(Goods product) {
		if ("".equals(product.getCategory())) {
			return false;
		}
		if ("".equals(product.getSubcategory())) {
			return false;
		}
		if ("".equals(product.getName())) {
			return false;
		}
		if ("".equals(product.getProducer())) {
			return false;
		}
		if (!isColorValid(product.getColor())) {
			return false;
		}
		if (!isModelValid(product.getModel())) {
			return false;
		}
		try {
			new SimpleDateFormat(Constants.DATE_PATTERN).parse(product
					.getDateOfIssue());
		} catch (ParseException ex) {
			return false;
		}
		if (product.getPrice() == 0 && !product.isNotInStock()) {
			return false;
		}
		if (product.getPrice() != 0 && product.isNotInStock()) {
			return false;
		}
		return true;
	}

	private static boolean isModelValid(String model) {
		Pattern pattern = Pattern.compile(Constants.MODEL_PATTERN);
		Matcher matcher = pattern.matcher(model);
		return matcher.matches();
	}

	private static boolean isColorValid(String color) {
		for (String colorStr : Constants.COLORS) {
			if (colorStr.equals(color)) {
				return true;
			}
		}
		return false;
	}
}
