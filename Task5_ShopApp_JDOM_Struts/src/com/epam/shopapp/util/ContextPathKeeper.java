package com.epam.shopapp.util;

public final class ContextPathKeeper {
	private static String contextPath;
	private static String goodsFilePath;

	private ContextPathKeeper() {
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String contextPath) {
		ContextPathKeeper.contextPath = contextPath;
	}

	public static String getGoodsFilePath() {
		return goodsFilePath;
	}

	public static void setGoodsFilePath(String goodsFilePath) {
		ContextPathKeeper.goodsFilePath = goodsFilePath;
	}

}
