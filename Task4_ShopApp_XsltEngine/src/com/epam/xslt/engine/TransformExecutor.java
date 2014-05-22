package com.epam.xslt.engine;

import static com.epam.xslt.resources.Constants.PARAM_TEMPLATE_ID;
import static com.epam.xslt.resources.Constants.PARAM_SOURCE_ID;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;

import java.io.File;

import com.epam.xslt.exception.TransformExecutionException;

import static com.epam.xslt.resources.Constants.*;

import com.epam.xslt.util.FormValidator;
import com.epam.xslt.util.XsltRequestUtil;
import com.epam.xslt.util.TransformerUtil;

public final class TransformExecutor {
	private static final Logger logger = Logger
			.getLogger(TransformExecutor.class);
	private static HashMap<String, ReentrantReadWriteLock> lockMap;

	private TransformExecutor() {
	}

	public static void init(Set<String> xmlResourceIdSet) {
		lockMap = new HashMap<>();
		Iterator<String> it = xmlResourceIdSet.iterator();
		while (it.hasNext()) {
			String xmlResourceId = it.next();
			lockMap.put(xmlResourceId, new ReentrantReadWriteLock());
		}
	}

	public static String execute(HttpServletRequest request,
			HttpServletResponse response) throws TransformExecutionException,
			IOException {
		Map<String, String> params = XsltRequestUtil.getParametersMap(request);
		lockMap.get(params.get(PARAM_SOURCE_ID)).readLock().lock();
		try {
			transform(params, new StreamResult(response.getWriter()));
			logger.debug("Transformation result is loaded to the response.");
		} finally {
			lockMap.get(params.get(PARAM_SOURCE_ID)).readLock().unlock();
		}
		return SUCCESS;
	}

	public static String executeXmlUpdate(HttpServletRequest request,
			HttpServletResponse response) throws TransformExecutionException {
		Map<String, String> params = XsltRequestUtil.getParametersMap(request);
		String sourceId = params.get(PARAM_SOURCE_ID);
		ByteArrayOutputStream transformResult = new ByteArrayOutputStream();
		File xmlFile;
		long lastModification;
		try {
			lockMap.get(sourceId).readLock().lock();
			try {
				xmlFile = new File(XmlResourceManager.getPath(sourceId));
				lastModification = xmlFile.lastModified();
				transform(params, new StreamResult(transformResult));
				logger.debug("Transformation result is loaded to temporary ByteArray.");
			} finally {
				lockMap.get(sourceId).readLock().unlock();
			}
			if (FormValidator.isValidRequest()) {
				lockMap.get(sourceId).writeLock().lock();
				FileOutputStream target = null;
				try {
					if (xmlFile.lastModified() != lastModification) {
						transformResult.reset();
						transform(params, new StreamResult(transformResult));
					}
					target = XmlResourceManager
							.getXmlFileOutputStream(sourceId);
					transformResult.writeTo(target);
					logger.debug("Transformation result is VALID. The xml file is updated.");
				} finally {
					if (target != null) {
						target.close();
					}
					lockMap.get(sourceId).writeLock().unlock();
				}
				if (!executeSuccessRedirect(params, response)) {
					transformResult.writeTo(response.getOutputStream());
				}
				return SUCCESS;
			} else {
				logger.debug("Transformation result is NOT VALID. The xml file is not updated.");
				if (!executeFailRedirect(params, response)) {
					transformResult.writeTo(response.getOutputStream());
				}
				return FAILED;
			}
		} catch (IOException ex) {
			logger.error(ex.getMessage());
			throw new TransformExecutionException(ex.getMessage());
		}
	}

	public static String executeTransformToNewFile(HttpServletRequest request,
			HttpServletResponse response) throws TransformExecutionException {
		Map<String, String> params = XsltRequestUtil.getParametersMap(request);
		String sourceId = params.get(PARAM_SOURCE_ID);
		String targetId = params.get(PARAM_TARGET_FILE_ID);
		String target;
		if (targetId != null) {
			target = XmlResourceManager.getPath(targetId);
		} else {
			target = params.get(PARAM_TARGET_FILE);
		}
		ByteArrayOutputStream transformResult = new ByteArrayOutputStream();
		File targetFile = null;
		try {
			lockMap.get(sourceId).readLock().lock();
			transform(params, new StreamResult(transformResult));
			logger.debug("Transformation result is loaded to temporary ByteArray.");
			if (FormValidator.isValidRequest()) {
				targetFile = new File(target);
				if (!lockMap.containsKey(targetFile.getPath())) {
					lockMap.put(targetFile.getPath(),
							new ReentrantReadWriteLock());
				}
				lockMap.get(targetFile.getPath()).writeLock().lock();
				try {
					FileOutputStream targetOS = new FileOutputStream(targetFile);
					transformResult.writeTo(targetOS);
					logger.debug("Transformation result is VALID. The result of transformation is saved to "
							+ targetFile.getAbsolutePath());
					if (!executeSuccessRedirect(params, response)) {
						transformResult.writeTo(response.getOutputStream());
					}
					return SUCCESS;
				} finally {
					lockMap.get(targetFile.getPath()).writeLock().unlock();
				}
			} else {
				logger.debug("Transformation result is NOT VALID. The xml file is not updated.");
				if (!executeFailRedirect(params, response)) {
					transformResult.writeTo(response.getOutputStream());
				}
				return FAILED;
			}
		} catch (IOException ex) {
			logger.error(ex.getMessage());
			throw new TransformExecutionException(ex.getMessage());
		} finally {
			lockMap.get(sourceId).readLock().unlock();
		}
	}

	private static void transform(Map<String, String> params, Result target)
			throws TransformExecutionException, IOException {
		String sourceId = params.get(PARAM_SOURCE_ID);
		String templateId = params.get(PARAM_TEMPLATE_ID);
		transform(sourceId, templateId, params, target);
		if (logger.isDebugEnabled()) {
			logger.debug("Transform executed successfully {sourceId=\""
					+ sourceId + "\", templateId=\"" + templateId + "\"}.");
		}
	}

	private static void transform(String xmlSourceId, String templateId,
			Map<String, String> params, Result target)
			throws TransformExecutionException {
		try {
			Source xmlSource = XmlResourceManager
					.getXmlStreamSource(xmlSourceId);
			Templates template = TemplatesManager.getTemplate(xmlSourceId,
					templateId);
			Transformer transformer = TransformerUtil
					.getParameterizedTransformer(template, params);
			transformer.transform(xmlSource, target);
		} catch (TransformerException ex) {
			String message = "Exception occured when attempting to transform xml source ID="
					+ xmlSourceId + " with teamplate ID=" + templateId + ". ";
			logger.error(message);
			throw new TransformExecutionException(message);
		}
	}

	/*
	 * returns <b>true</b> if redirection is possible based on request
	 * parameters and <b>false</b> if the appropriate parameters are undefined
	 */
	public static boolean executeSuccessRedirect(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			TransformExecutionException {
		Map<String, String> params = XsltRequestUtil.getParametersMap(request);
		return executeSuccessRedirect(params, response);
	}

	/*
	 * returns <b>true</b> if redirection is possible based on request
	 * parameters and <b>false</b> if the appropriate parameters are undefined
	 */
	public static boolean executeSuccessRedirect(Map<String, String> params,
			HttpServletResponse response) throws IOException,
			TransformExecutionException {
		String sourceId = params.get(PARAM_REDIRECT_XML_SOURCE_ID);
		String templateId = params.get(PARAM_REDIRECT_TEMPLATE_ID);
		String redirectURL;
		if (sourceId != null && templateId != null) {
			lockMap.get(sourceId).readLock().lock();
			try {
				transform(sourceId, templateId, params, new StreamResult(
						response.getWriter()));
				logger.debug("Transformation redirection processing...");
			} finally {
				lockMap.get(sourceId).readLock().unlock();
			}
			return true;
		} else if ((redirectURL = params.get(PARAM_REDIRECT_URL)) != null) {
			response.sendRedirect(redirectURL);
			if (logger.isDebugEnabled()) {
				logger.debug("Redirection to " + redirectURL + " processing...");
			}
			return true;
		}
		logger.debug("No redirection parameters are found amoung request parameters");
		return false;
	}

	/*
	 * returns <b>true</b> if redirection is possible based on request
	 * parameters and <b>false</b> if the appropriate parameters are undefined
	 */
	public static boolean executeFailRedirect(Map<String, String> params,
			HttpServletResponse response) throws IOException,
			TransformExecutionException {
		String sourceId = params.get(PARAM_ERROR_REDIRECT_XML_SOURCE_ID);
		String templateId = params.get(PARAM_ERROR_REDIRECT_TEMPLATE_ID);
		String redirectURL;
		if (sourceId != null && templateId != null) {
			lockMap.get(sourceId).readLock().lock();
			try {
				transform(sourceId, templateId, params, new StreamResult(
						response.getWriter()));
				logger.debug("Transformation redirection processing...");
			} finally {
				lockMap.get(sourceId).readLock().unlock();
			}
			return true;
		} else if ((redirectURL = params.get(PARAM_ERROR_REDIRECT_URL)) != null) {
			response.sendRedirect(redirectURL);
			if (logger.isDebugEnabled()) {
				logger.debug("Redirection to " + redirectURL + " processing...");
			}
			return true;
		}
		logger.debug("No error redirection parameters are found amoung request parameters.");
		return false;
	}
}
