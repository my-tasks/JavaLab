<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Goods List</title>
<link rel='stylesheet' type='text/css' href='./resources/css/style.css' />
</head>
<script type='text/javascript' src='./resources/js/script.js'></script>
<body>
	<div class='centered'>
		<html:form method="post" action="list" styleId="form">
			<html:hidden name="goodsForm" property="currCategory"
				value="${goodsForm.currCategory}" />
			<html:hidden name="goodsForm" property="currSubcategory"
				value="${goodsForm.currSubcategory}" />
			<input type="hidden" id="method" name="method" />
			<table class="main">
				<tr>
					<td class='title'>
						<H2>
							<a class="list" href="./index.jsp"> Main</a>
							&#160;&#160;&#160;/&#160;&#160;&#160; <a class="list"
								href="./list.do"
								onclick="set('method', 'showSubcategories'); submit('form'); return false">
								<bean:write name="goodsForm" property="currCategory" />
							</a> &#160;&#160;&#160;/&#160;&#160;&#160;
							<bean:write name="goodsForm" property="currSubcategory" />
						</H2>
						<hr />
					</td>
				</tr>
				<tr>
					<td class="content">
						<table class="list">
							<TR>
								<th>Product name</th>
								<th>Producer</th>
								<th>Model</th>
								<th>Date of issue</th>
								<th>Color</th>
								<th>Price</th>
								<th>Not in stock</th>
							</TR>

							<logic:iterate name="goodsForm"
								property="document.rootElement.children" id="categories"
								indexId="cIndex">
								<logic:match name="categories" property="attributes[0].value"
									value="${goodsForm.currCategory }">
									<bean:define id="index" value="${cIndex}" />
									<nested:nest property="document.rootElement.children">
										<logic:iterate id="subcategory" name="categories"
											property="children">
											<logic:match name="subcategory"
												property="attributes[0].value"
												value="${goodsForm.currSubcategory }">
												<logic:iterate id="goods" name="subcategory"
													property="children">
													<TR>
														<TD class="goods" title='Product Name'>
															<%-- 														<nested:text property="attributes[0].value"/> --%>
															<%-- 														<html:text --%> <%-- 																property="attributes[0].value" size="10" /> --%>

														</TD>
														<TD class="goods" title='Provider'><html:text
																name="goods" property="child(producer).text" size="10">
															</html:text></TD>
														<TD class="goods" title='Model'><html:text
																name="goods" property="child(model).text" size="10" /></TD>
														<TD class="goods" title='Date of issue'><html:text
																name="goods" property="child(date-of-issue).text"
																size="10" /></TD>
														<TD class="goods" title='Color'><html:text
																name="goods" property="child(color).text" size="10" /></TD>
														<logic:empty name="goods" property="child(price)">

														</logic:empty>

														<c:choose>
															<c:when test="${goods.children[4].name eq 'price' }">
																<TD class="goods" title='Price'><html:text
																		name="goods" property="child(price).text" size="10" /></TD>
															</c:when>
															<c:otherwise>
																<TD class="goods" title='Not in stock'><div
																		class="not-in-stock">NOT IN STOCK</div></TD>
															</c:otherwise>
														</c:choose>
														<TD class="goods" title='Not In Stock'></TD>
													</TR>
												</logic:iterate>
											</logic:match>
										</logic:iterate>
									</nested:nest>
								</logic:match>
							</logic:iterate>
							<hr>
							<hr>
							<hr>

							<logic:iterate id="category" name="goodsForm"
								property="document.rootElement.children">
								<logic:match name="category" property="attributes[0].value"
									value="${goodsForm.currCategory }">
									<logic:iterate id="subcategory" name="category"
										property="children">
										<logic:match name="subcategory" property="attributes[0].value"
											value="${goodsForm.currSubcategory }">
											<logic:iterate id="goods" name="subcategory"
												property="children">
												<%-- 											<nested:nest property="children"> --%>
												<TR>
													<TD class="goods" title='Product Name'><html:text
															name="goods" property="attributes[0].value" size="10" /></TD>
													<TD class="goods" title='Provider'><html:text
															name="goods" property="child(producer).text" size="10">
														</html:text></TD>
													<TD class="goods" title='Model'><html:text
															name="goods" property="child(model).text" size="10" /></TD>
													<TD class="goods" title='Date of issue'><html:text
															name="goods" property="child(date-of-issue).text"
															size="10" /></TD>
													<TD class="goods" title='Color'><html:text
															name="goods" property="child(color).text" size="10" /></TD>
													<logic:empty name="goods" property="child(price)">

													</logic:empty>

													<c:choose>
														<c:when test="${goods.children[4].name eq 'price' }">
															<TD class="goods" title='Price'><html:text
																	name="goods" property="child(price).text" size="10" /></TD>
														</c:when>
														<c:otherwise>
															<TD class="goods" title='Not in stock'><div
																	class="not-in-stock">NOT IN STOCK</div></TD>
														</c:otherwise>
													</c:choose>
													<TD class="goods" title='Not In Stock'></TD>
												</TR>
											</logic:iterate>
											<%-- 											</nested:nest> --%>
										</logic:match>
									</logic:iterate>
								</logic:match>
							</logic:iterate>
						</table>
				<tr>
					<td colspan="6" class="inline-block">
						<button
							onclick="set('method', 'showSubcategories'); submit('form'); return false">
							Back</button>
						<button
							onclick="set('method', 'update'); submit('form'); return false">
							Update</button>
						<button onclick="submit('addForm'); return false">Add
							goods</button>
					</td>
				</tr>
			</table>
		</html:form>
	</div>
</body>
</html>

