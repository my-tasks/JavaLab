package com.epam.xslt.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.xslt.config.ConfigurationLoader;
import com.epam.xslt.engine.TransformExecutor;
import com.epam.xslt.exception.BadFormedRequest;
import com.epam.xslt.exception.TransformExecutionException;
import com.epam.xslt.util.LogUtil;
import com.epam.xslt.util.SafeRequestUtil;

import static com.epam.xslt.resources.Constants.*;

public class XslTransformAction extends HttpServlet {
	private static final Logger logger = Logger
			.getLogger(XslTransformAction.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		String contextPath = getServletContext().getRealPath("/");
		String configFilePath = contextPath
				+ this.getInitParameter(INIT_PARAM_NAME);
		ConfigurationLoader.loadConfiguration(configFilePath, contextPath);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug(LogUtil.getRequestParameteresLog(request));
		}
		if (SafeRequestUtil.isSafeRequest(request)) {
			SafeRequestUtil.setSafetyKey(request);
		}
		try {
			if (SafeRequestUtil.isValidRequest(request)) {
				String requestType = request.getParameter(PARAM_REQUEST_TYPE) != null ? request
						.getParameter(PARAM_REQUEST_TYPE) : DEFAULT;
				if (logger.isDebugEnabled()) {
					logger.debug("Request Type: " + requestType);
				}
				String executionResult;
				switch (requestType) {
				case (DEFAULT):
					executionResult = TransformExecutor.execute(request,
							response);
					break;
				case (REQUEST_TYPE_NEW_FILE):
					executionResult = TransformExecutor
							.executeTransformToNewFile(request, response);
					break;
				case (REQUEST_TYPE_XML_UPDATE):
					executionResult = TransformExecutor.executeXmlUpdate(
							request, response);
					break;
				default:
					throw new BadFormedRequest(requestType + "\" is unknown. "
							+ "Only {default, xmlUpdate}"
							+ " values are valid.");
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Transform executed with result: "
							+ executionResult);
				}
				if (SUCCESS.equals(executionResult)
						&& SafeRequestUtil.isSafetyKeyAlreadySet(request)) {
					SafeRequestUtil.resetSessionSafetyKey(request);
				}
			} else {
				TransformExecutor.executeSuccessRedirect(request, response);
			}
		} catch (TransformExecutionException ex) {
			logger.error(ex.getMessage());
			response.sendError(500);
		} catch (BadFormedRequest ex) {
			logger.error("Request failed to be processed: " + ex.getMessage());
			ex.printStackTrace();
			response.sendError(500);
		} catch (NullPointerException ex) {
			logger.error("Request failed to be processed: probably some of "
					+ "request parameters are invalid");
			ex.printStackTrace();
			response.sendError(404);
		}
	}
}
