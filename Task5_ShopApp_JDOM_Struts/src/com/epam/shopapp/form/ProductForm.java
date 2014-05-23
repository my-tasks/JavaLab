package com.epam.shopapp.form;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import com.epam.shopapp.model.Product;
import com.epam.shopapp.resources.Constants;
import com.epam.shopapp.util.FormValidator;

public final class ProductForm extends ActionForm {
	private Document document;
	private Product newProduct;
	private Integer categoryIndex;
	private String categoryName;
	private Integer subcategoryIndex;
	private String subcategoryName;
	private Integer[] selectedNotInStock;

	public ProductForm() {
		super();
		System.out.println(">>>>>  +++ NEW FORM +++  <<<<<");
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Product getNewProduct() {
		if (newProduct == null) {
			newProduct = new Product();
		}
		return newProduct;
	}

	public void setNewProduct(Product newProduct) {
		this.newProduct = newProduct;
	}

	public Integer getCategoryIndex() {
		return categoryIndex;
	}

	public void setCategoryIndex(Integer categoryIndex) {
		this.categoryIndex = categoryIndex;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getSubcategoryIndex() {
		return subcategoryIndex;
	}

	public void setSubcategoryIndex(Integer subcategoryIndex) {
		this.subcategoryIndex = subcategoryIndex;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}


	public Integer[] getSelectedNotInStock() {
		return selectedNotInStock;
	}

	public void setSelectedNotInStock(Integer[] selectedNotInStock) {
		this.selectedNotInStock = selectedNotInStock;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		System.out.println(">>>>   FORM RESET   <<<<");
		selectedNotInStock = new Integer[0];
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		System.out.println("validation");
		if (newProduct != null) {
			if (newProduct.getName().trim().isEmpty()) {
				errors.add("name", new ActionMessage("errors.empty.field"));
			}
			if (newProduct.getProducer().trim().isEmpty()) {
				errors.add("producer", new ActionMessage("errors.empty.field"));
			}
			if (newProduct.getModel().trim().isEmpty()) {
				errors.add("model", new ActionMessage("errors.empty.field"));
			} else if (!FormValidator.isValidModel(newProduct.getModel())) {
				errors.add("model", new ActionMessage(
						"errors.incorrect.format", Constants.MODEL_FORMAT));
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
		}
		System.out.println(Arrays.toString(selectedNotInStock));
		return errors;
	}
}
