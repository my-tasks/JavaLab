package com.epam.shopapp.action;

import static com.epam.shopapp.resources.Constants.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

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
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
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
	private Templates addProductTemplates;
	private String filePath = ContextPathKeeper.getContextPath()
			+ ContextPathKeeper.getGoodsFilePath();
	private Document document;

	public ProductAction() throws Exception {
		super();
		ShopFileLocker.getReadLock().lock();
		try {
			System.out.println("====>   START:  initializing ");
			document = new SAXBuilder().build(filePath);
			System.out.println("====>   END:  initializing ");
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
		System.out.println("====>   START:  show Categories ");
		ProductForm productForm = (ProductForm) form;
		ShopFileLocker.getReadLock().lock();
		try {
			productForm.setDocument(document);
		} finally {
			ShopFileLocker.getReadLock().unlock();
		}
		System.out.println("====>   END:  show Categories ");
		return (mapping.findForward(SHOW_CATEGORIES));
	}

	public ActionForward showSubcategories(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("====>   START:  show SubCategories ");
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
		System.out.println("====>   END:  show SubCategories ");
		return mapping.findForward(SHOW_SUBCATEGORIES);
	}

	public ActionForward showProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("====>   START:  show Products ");
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
		System.out.println("====>   END:  show Products ");
		return mapping.findForward(SHOW_PRODUCTS);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		System.out.println("====>   START:  update Products ");
		String target = SHOW_PRODUCTS;
		ProductForm productForm = (ProductForm) form;
		Document doc = productForm.getDocument();
		System.out.println(doc);
		if (doc != null) {

			/*
			 * ShopFileLocker.getWriteLock().lock(); try { XMLOutputter
			 * xmlOutputter = new XMLOutputter(); xmlOutputter.output(doc, new
			 * FileOutputStream(filePath)); } finally {
			 * ShopFileLocker.getWriteLock().unlock(); }
			 */
		} else {
			target = ERROR;
			System.out.println("METHOD-ERROR: update: document==null");
			// TODO бросаем свое исключение
		}
		productForm.setDocument(document);
		System.out.println("====>   END:  update Products ");
		return (mapping.findForward(target));
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

	public ActionForward saveProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		String target = SHOW_PRODUCTS;
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
				target = ERROR;
				logger.error(ex.getMessage());
			}
		}
		ShopFileLocker.getReadLock().lock();
		try {
			productForm.setDocument(document);
		} finally {
			ShopFileLocker.getReadLock().unlock();
		}
		return mapping.findForward(target);
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
