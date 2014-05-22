package com.epam.shopapp.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ContextPathLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ContextPathKeeper.setContextPath(getServletContext().getRealPath("/"));
		ContextPathKeeper.setGoodsFilePath(config
				.getInitParameter("goods-file"));
	}
}
