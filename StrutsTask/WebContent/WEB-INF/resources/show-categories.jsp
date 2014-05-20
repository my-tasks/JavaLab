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
			<input type="hidden" id="currCategory" name="currCategory" /> <input
				type="hidden" name="method" value="showSubcategories" />
			<table class="main">
				<tr>
					<td class='title'>
						<H2>
							<a class="list" href="./index.jsp"> Main </a>
						</H2>
						<hr />
					</td>
				</tr>
				<tr>
					<td class="content">
						<table class="list">
							<tr>
								<th>Category name</th>
								<th>Goods quantity</th>
							</tr>
							<nested:iterate id="item" name="goodsForm"
								property="document.rootElement.children">
								<tr>
									<td class='category'><c:set var="category">
											<nested:define id="category" property="attributes[0].value" />
										</c:set> <a class='list' href='./list.do'
										onclick="set('currCategory', '${category}'); submit('form'); return false">
											<nested:write name="category" />
									</a></td>

									<!-- Counting goods quantity for the current category -->
									<c:set var="size" value="0" scope="page" />
									<logic:iterate id="subcategory" name="item" property="children">
										<bean:size id="sizeOf" name="subcategory" property="children" />
										<c:set var="size" value="${size+sizeOf}" />
									</logic:iterate>
									<td class="category">( ${size} )</td>
								</tr>
							</nested:iterate>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
