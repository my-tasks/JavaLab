package com.epam.shopapp.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Element;

import com.epam.shopapp.model.Product;
import com.epam.shopapp.resources.Constants;

public class FormValidator {
	private static final String DATE_PATTERN = "(((0)[1-9]|[1,2][0-9]|(3)[0-1])-((0)[1,3,5,7,8]|(1)[0,2])-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9]|(30))-((0)[4,6,9]|(11))-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9])-(02)-((20)[0-9][0-9]))";
	private static final String MODEL_PATTERN = "[\u0430-\u044F\u0410-\u044Fa-zA-Z][\u0430-\u044F\u0410-\u044Fa-zA-Z][0-9][0-9][0-9]";
	private static final String PRICE_PATTERN = "[0-9]+";

	private FormValidator() {
	}

	public static ActionErrors validateProductsUpdated(List<Element> products,
			List<Integer> notInStock) {
		ActionErrors errors = new ActionErrors();
		for (int i = 0; i < products.size(); i++) {
			Element product = products.get(i);
			if (product.getAttributes().get(0).getValue().isEmpty()) {
				errors.add("common_error", new ActionMessage(
						"errors.incorrect.values"));
				return errors;
			}
			List<Element> productFields = product.getChildren();
			for (Element productField : productFields) {
				if (productField.getText().trim().isEmpty()) {
					if (Constants.NOT_IN_STOCK.equals(productField.getName())
							&& !notInStock.contains(i)) {
						errors.add("common_error", new ActionMessage(
								"errors.incorrect.values"));
					}
					return errors;
				}
			}
			if (!FormValidator.isValidModel(product.getChildren().get(1)
					.getText().trim())) {
				errors.add("common_error", new ActionMessage(
						"errors.incorrect.values"));
				return errors;
			}
			if (!FormValidator.isValidDate(product.getChildren().get(2)
					.getText().trim())) {
				errors.add("common_error", new ActionMessage(
						"errors.incorrect.values"));
				return errors;
			}
			if (!"true".equals(product.getChildren().get(4).getText().trim())) {
				if (!FormValidator.isValidPrice(product.getChildren().get(4)
						.getText().trim())) {
					errors.add("common_error", new ActionMessage(
							"errors.incorrect.values"));
					return errors;
				}
			}
		}
		return errors;
	}

	public static ActionErrors validateNewProduct(Product newProduct) {
		ActionErrors errors = new ActionErrors();
		if (newProduct.getName().trim().isEmpty()) {
			errors.add("name", new ActionMessage("errors.empty.field"));
		}
		if (newProduct.getProducer().trim().isEmpty()) {
			errors.add("producer", new ActionMessage("errors.empty.field"));
		}
		if (newProduct.getModel().trim().isEmpty()) {
			errors.add("model", new ActionMessage("errors.empty.field"));
		} else if (!FormValidator.isValidModel(newProduct.getModel())) {
			errors.add("model", new ActionMessage("errors.incorrect.format",
					Constants.MODEL_FORMAT));
		}
		if (newProduct.getDateOfIssue().trim().isEmpty()) {
			errors.add("date", new ActionMessage("errors.empty.field"));
		} else if (!FormValidator.isValidDate(newProduct.getDateOfIssue())) {
			errors.add("date", new ActionMessage("errors.incorrect.format",
					Constants.DATE_FORMAT));
		}
		if (newProduct.getColor().trim().isEmpty()) {
			errors.add("color", new ActionMessage("errors.empty.color"));
		}
		if (!newProduct.isNotInStock()) {
			if (newProduct.getPrice() == null || newProduct.getPrice() == 0) {
				errors.add("price", new ActionMessage("errors.empty.field"));
			}
		}
		return errors;
	}

	public static boolean isEmptyFieldValue(String field) {
		if (field == null || field.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmptyFieldValue(String field) {
		if (field == null || field.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static boolean isCheckboxChecked(String notInStock) {
		if ("on".equals(notInStock)) {
			return true;
		}
		return false;
	}

	public static boolean isValidDate(String token) {
		Pattern pattern = Pattern.compile(DATE_PATTERN);
		Matcher matcher = pattern.matcher(token.trim());
		return matcher.matches();
	}

	public static boolean isValidModel(String token) {
		Pattern pattern = Pattern.compile(MODEL_PATTERN);
		Matcher matcher = pattern.matcher(token.trim());
		return matcher.matches();
	}

	public static boolean isValidPrice(String token) {
		Pattern pattern = Pattern.compile(PRICE_PATTERN);
		Matcher matcher = pattern.matcher(token.trim());
		return matcher.matches();
	}
}
