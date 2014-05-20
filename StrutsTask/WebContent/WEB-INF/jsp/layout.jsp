<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Goods List</title>
<link rel='stylesheet' type='text/css' href='./resources/css/style.css' />
</head>
<script type='text/javascript' src='./resources/js/script.js'></script>
<body>
	<div class='centered'>
		<table class="main">
			<tr>
				<td class='title'>
					<H2>
						<a class="list" href="./index.jsp"><bean:message
								key="label.main" /></a>
<%-- 						<logic:notEmpty name="productForm" property="categoryIndex">
							&#160;&#160;&#160;/&#160;&#160;&#160;
							<html:link styleClass="list"
								action="/list.do?method=showSubcategories&categoryIndex=${productForm.categoryIndex}">
								<bean:write name="productForm"
									property="document.rootElement.children[${productForm.categoryIndex}].attributes[0].value" />
							</html:link>
							<logic:notEmpty name="productForm" property="subcategoryIndex"> >
							&#160;&#160;&#160;/&#160;&#160;&#160;
 									<html:link styleClass="list"
									action="/list.do?method=showProducts&categoryIndex=${productForm.categoryIndex}&subcategoryIndex=${productForm.subcategoryIndex}">
									<bean:write name="productForm"
										property="document.rootElement.children[${productForm.categoryIndex}].children[${productForm.subcategoryIndex}].attributes[0].value" />
								</html:link>
							</logic:notEmpty>
						</logic:notEmpty> --%>
					</H2>
					<hr />
				</td>
			</tr>
			<tr>
				<td class="content"><tiles:insert attribute="body" /></td>
			</tr>
		</table>
	</div>
</body>
</html>
