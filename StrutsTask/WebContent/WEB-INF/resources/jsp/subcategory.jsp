<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/list" method="post" onsubmit="alert('dd')">
	<html:hidden property="method" styleId="method" value="showGoods"/>
	<html:hidden property="currCategory" styleId="currCategory" value="${goodsForm.currCategory}"/>
	<html:hidden property="currSubcategory" styleId="currSubcategory"/>
	<table class="list">
		<tr>
			<th>Subcategory name</th>
			<th>Goods quantity</th>
		</tr>
		<nested:iterate property="document.rootElement.children">
			<nested:equal value="${goodsForm.currCategory}" property="attributes[0].value">
				<nested:iterate property="children">
					<tr>
						<td class='category'>
							<nested:define id="subcategory"	property="attributes[0].value" /> 
							<html:link styleClass="list" page="/list" 
 								onclick="set('currSubcategory', '${subcategory}'); submitForm(); return false"> 
									<nested:write property="attributes[0].value" />
							</html:link> 
						</td>
						<!-- Counting goods quantity for the current subcategory -->
						<nested:size id="size" property="children" />
						<td class="category">${size}</td>
					</tr>
				</nested:iterate>
			</nested:equal>
		</nested:iterate>
	</table>
</html:form>
