package com.epam.shopapp.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.jdom2.Document;
import org.jdom2.Element;

import com.epam.shopapp.model.Product;
import com.epam.shopapp.util.FormValidator;

public final class ProductForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private Document document;
	private Product newProduct;
	private Integer categoryIndex;
	private String categoryName;
	private Integer subcategoryIndex;
	private String subcategoryName;
	private Integer[] selectedNotInStock;

	public ProductForm() {
		super();
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
		selectedNotInStock = new Integer[0];
		newProduct = null;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		if (newProduct != null) {
			return FormValidator.validateNewProduct(newProduct);
		} else if (document != null) {
			List<Element> products = document.getRootElement().getChildren()
					.get(categoryIndex).getChildren().get(subcategoryIndex)
					.getChildren();
			return FormValidator.validateProductsUpdated(products,
					new ArrayList<Integer>(Arrays.asList(selectedNotInStock)));
		} else {
			return new ActionErrors();
		}
	}
}
