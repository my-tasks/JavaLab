<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<tiles:importAttribute name="title" scope="request" />
<title><bean:message key="${title}" /></title>
<link rel="stylesheet" type="text/css" href="./resources/css/style.css"
	media="all">
</head>
<body>
	<div class="center">
		<table class="main">
			<tr class="main" id="header">
				<td colspan="2"><tiles:insert attribute="header" /></td>
			</tr>
			<tr class="main" id="middle">
				<td class="main" id="menu"><tiles:insert attribute="menu" /></td>
				<td class="main" id="body"><div class="main">
						<tiles:insert attribute="body" />
					</div></td>
			</tr>
			<tr class="main" id="footer">
				<td colspan="2"><tiles:insert attribute="footer" /></td>
			</tr>
		</table>
	</div>
</body>
</html>