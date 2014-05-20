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
						<a class="list" href="./index.jsp"> Main</a>
						<logic:notEmpty name="goodsForm" property="currCategory">
							&#160;&#160;&#160;/&#160;&#160;&#160;
							<html:link styleClass="list" page="/list" 
 								onclick="set('currCategory', '${goodsForm.currCategory}'); set('method', 'showSubcategories'); submitForm(); return false"> 
								<bean:write name="goodsForm" property="currCategory" />
							</html:link>  
							<logic:notEmpty name="goodsForm" property="currSubcategory">
								&#160;&#160;&#160;/&#160;&#160;&#160;
								<html:link styleClass="list" page="/list" 
 									onclick="set('currCategory', '${goodsForm.currCategory}'); set('currSubcategory', '${goodsForm.currSubcategory}'); set('method', 'showGoods'); submitForm(); return false"> 
									<bean:write name="goodsForm" property="currSubcategory" />
								</html:link>  
							</logic:notEmpty>
						</logic:notEmpty>
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
