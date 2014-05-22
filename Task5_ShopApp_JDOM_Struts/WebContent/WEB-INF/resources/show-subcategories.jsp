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
		<form id="form" action="./list.do" method="post">
			<input type="hidden" id="currCategory" name="currCategory"
				value="${goodsForm.currCategory}" /> <input type="hidden"
				id="currSubcategory" name="currSubcategory" /> <input type="hidden"
				name="method" value="showGoods" />
			<table class="main">
				<tr>
					<td class='title'>
						<H2>
							<a class="list" href="./index.jsp"> Main</a>
							&#160;&#160;&#160;/&#160;&#160;&#160;
							<bean:write name="goodsForm" property="currCategory" />
						</H2>
						<hr />
					</td>
				</tr>
				<tr>
					<td class="content">
						<table class="list">
							<tr>
								<th>Subcategory name</th>
								<th>Goods quantity</th>
							</tr>

							<logic:iterate id="items" name="goodsForm"
								property="document.rootElement.children">
								<c:if
									test="${items.attributes[0].value eq goodsForm.currCategory}">
									<logic:iterate id="item" name="items" property="children">
										<tr>
											<td class='category'><c:set var="subcategory">
													<bean:write name="item" property="attributes[0].value" />
												</c:set> <a class='list' href='./list.do'
												onclick="set('currSubcategory', '${subcategory}'); submit('form'); return false">
													<bean:write name="subcategory" />
											</a></td>
											<!-- Counting goods quantity for the current category -->
											<bean:size id="size" name="item" property="children" />
											<td class="category">( ${size} )</td>
										</tr>
									</logic:iterate>
								</c:if>
							</logic:iterate>
						</table>
				<tr>
					<td colspan="2" class="inline-block">
						<button
							onclick="window.location='./list.do?method=showCategories'; return false">
							Back</button>
					</td>
				</tr>

			</table>
	</div>
</body>
</html>


