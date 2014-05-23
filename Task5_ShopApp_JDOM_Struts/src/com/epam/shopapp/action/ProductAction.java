package com.epam.shopapp.action;

import static com.epam.shopapp.resources.Constants.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.JDOMResult;
import org.jdom2.transform.JDOMSource;

import com.epam.shopapp.form.ProductForm;
import com.epam.shopapp.model.Product;
import com.epam.shopapp.resources.Constants;
import com.epam.shopapp.util.ContextPathKeeper;
import com.epam.shopapp.util.ShopFileLocker;

public final class ProductAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger(ProductAction.class);
	private final String SHOW_CATEGORIES = "showCategories";
	private final String SHOW_SUBCATEGORIES = "showSubcategories";
	private final String SHOW_PRODUCTS = "showProducts";
	private final String ADD_PRODUCT = "addProduct";
	private final String ERROR = "error";
	private final String SHOW_PRODUCTS_REDIRECT = "/list.do?method=showProducts&categoryIndex=%d&subcategoryIndex=%d";
	private Templates addProductTemplates;
	private String filePath = ContextPathKeeper.getContextPath()
			+ ContextPathKeeper.getGoodsFilePath();
	private Document document;

	public ProductAction() throws Exception {
		super();
		ShopFileLocker.getReadLock().lock();
		try {
			document = new SAXBuilder().build(filePath);
		} finally {
			ShopFileLocker.getReadLock().unlock();
		}
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Source xslValidateSource = new StreamSource(new File(
					ContextPathKeeper.getContextPath()
							+ Constants.ADD_PRODUCT_XSL));
			addProductTemplates = factory.newTemplates(xslValidateSource);
		} catch (TransformerConfigurationException ex) {
			logger.error("Impossible to initialize ProductAction: "
					+ ex.getMessage());
			throw ex;
		}
	}

	public ActionForward showCategories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ProductForm productForm = (ProductForm) form;
		ShopFileLocker.getReadLock().lock();
		try {
			productForm.setDocument(document);
		} finally {
			ShopFileLocker.getReadLock().unlock();
		}
		return (mapping.findForward(SHOW_CATEGORIES));
	}

	public ActionForward showSubcategories(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProductForm productForm = (ProductForm) form;
		Integer categoryIndex = productForm.getCategoryIndex();
		if (categoryIndex != null) {
			productForm.setCategoryIndex(categoryIndex);
			ShopFileLocker.getReadLock().lock();
			try {
				productForm.setDocument(document);
			} finally {
				ShopFileLocker.getReadLock().unlock();
			}
		} else {
			throw new InvalidParameterException(
					"Can not find category index parameter");
		}
		return mapping.findForward(SHOW_SUBCATEGORIES);
	}

	public ActionForward showProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ProductForm productForm = (ProductForm) form;
		Integer categoryIndex = productForm.getCategoryIndex();
		Integer subcategoryIndex = productForm.getSubcategoryIndex();
		if (categoryIndex != null && subcategoryIndex != null) {
			productForm.setCategoryIndex(categoryIndex);
			productForm.setSubcategoryIndex(subcategoryIndex);
			ShopFileLocker.getReadLock().lock();
			try {
				productForm.setDocument(document);
			} finally {
				ShopFileLocker.getReadLock().unlock();
			}
		} else {
			throw new InvalidParameterException(
					"Can not find category or subcategory indexes parameters");
		}
		return mapping.findForward(SHOW_PRODUCTS);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		System.out.println("update");
		ProductForm productForm = (ProductForm) form;
		Integer categoryIndex = productForm.getCategoryIndex();
		Integer subcategoryIndex = productForm.getSubcategoryIndex();
		Document doc = productForm.getDocument();
		Integer[] selected = productForm.getSelectedNotInStock();
		if (doc != null) {
			ShopFileLocker.getWriteLock().lock();
			try {
				List<Element> products = doc.getRootElement().getChildren()
						.get(categoryIndex).getChildren().get(subcategoryIndex)
						.getChildren();
				for(Element product: products){
					product.getChildren().get(4).setName(Constants.PRICE);
				}
				for(int k: selected){
					Element elem = 
					products.get(k).getChildren().get(4);
					elem.setText("true");
					elem.setName(Constants.NOT_IN_STOCK);
				}
				XMLOutputter xmlOutputter = new XMLOutputter();
				xmlOutputter.output(doc, new FileOutputStream(filePath));
			} finally {
				ShopFileLocker.getWriteLock().unlock();
			}
		} else {
			return (mapping.findForward(ERROR));
		}
		productForm.setDocument(document);
		String path = String.format(SHOW_PRODUCTS_REDIRECT, categoryIndex,
				subcategoryIndex);
		ActionRedirect actionRedirect = new ActionRedirect(path);
		return actionRedirect;
	}

	public ActionForward addProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		ProductForm productForm = (ProductForm) form;
		productForm.setNewProduct(new Product());
		ShopFileLocker.getReadLock().lock();
		Integer categoryIndex = productForm.getCategoryIndex();
		Integer subcategoryIndex = productForm.getSubcategoryIndex();
		if (categoryIndex != null && subcategoryIndex != null) {
			try {
				productForm.setCategoryIndex(categoryIndex);
				productForm.setSubcategoryIndex(subcategoryIndex);
				productForm.setCategoryName(getCategoryName(categoryIndex));
				productForm.setSubcategoryName(getSubcategoryName(
						categoryIndex, subcategoryIndex));
			} finally {
				ShopFileLocker.getReadLock().unlock();
			}
		} else {
			throw new InvalidParameterException(
					"Can not find category or subcategory indexes parameters");
		}
		saveToken(request);
		return (mapping.findForward(ADD_PRODUCT));
	}

	public ActionForward saveProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		ProductForm productForm = (ProductForm) form;
		Integer categoryIndex = productForm.getCategoryIndex();
		Integer subcategoryIndex = productForm.getSubcategoryIndex();
		productForm.setCategoryIndex(categoryIndex);
		productForm.setSubcategoryIndex(subcategoryIndex);
		if (isTokenValid(request, true)) {
			try {
				Product newProduct = productForm.getNewProduct();
				Transformer transformer = addProductTemplates.newTransformer();
				setParametersForSaving(transformer, newProduct);
				ShopFileLocker.getWriteLock().lock();
				try {
					Source sourceDocument = new JDOMSource(document);
					JDOMResult resultDocument = new JDOMResult();
					transformer.transform(sourceDocument, resultDocument);
					OutputStream targetFileOS = new FileOutputStream(filePath);
					XMLOutputter out = new XMLOutputter();
					out.output(resultDocument.getDocument(), targetFileOS);
					targetFileOS.flush();
					targetFileOS.close();
					document = new SAXBuilder().build(filePath);
				} finally {
					ShopFileLocker.getWriteLock().unlock();
				}
			} catch (TransformerException ex) {
				logger.error(ex.getMessage());
				return mapping.findForward(ERROR);
			}
		}
		ShopFileLocker.getReadLock().lock();
		try {
			productForm.setDocument(document);
		} finally {
			ShopFileLocker.getReadLock().unlock();
		}
		String path = String.format(SHOW_PRODUCTS_REDIRECT, categoryIndex,
				subcategoryIndex);
		ActionRedirect actionRedirect = new ActionRedirect(path);
		return actionRedirect;
	}

	public ActionForward error(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		System.out.println("ERROR!");
		return (mapping.findForward(ERROR));
	}

	public ActionForward error404(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		System.out.println("ERROR 404");
		return (mapping.findForward(ERROR));
	}

	public String getCategoryName(int categoryIndex) {
		return document.getRootElement().getChildren().get(categoryIndex)
				.getAttributeValue(NAME);
	}

	public String getSubcategoryName(int categoryIndex, int subcategoryIndex) {
		return document.getRootElement().getChildren().get(categoryIndex)
				.getChildren().get(subcategoryIndex).getAttributeValue(NAME);
	}

	private void setParametersForSaving(Transformer transformer,
			Product newProduct) {
		transformer.setParameter(CATEGORY_NAME, newProduct.getCategory());
		transformer.setParameter(SUBCATEGORY_NAME, newProduct.getSubcategory());
		transformer.setParameter(NAME, newProduct.getName());
		transformer.setParameter(PRODUCER, newProduct.getProducer());
		transformer.setParameter(MODEL, newProduct.getModel());
		transformer.setParameter(COLOR, newProduct.getColor());
		transformer.setParameter(DATE_OF_ISSUE, newProduct.getDateOfIssue());
		if (!newProduct.isNotInStock()) {
			transformer.setParameter(PRICE,
					String.valueOf(newProduct.getPrice()));
		}
	}
}
