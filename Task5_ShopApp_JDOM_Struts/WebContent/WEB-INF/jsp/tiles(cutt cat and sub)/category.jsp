<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/list" method="post">
	<html:hidden property="currCategory" styleId="currCategory"/>
	<html:hidden property="method" styleId="method" value="showSubcategories"/>
	<table class="list">
		<tr>
			<th>Category name</th>
			<th>Goods quantity</th>
		</tr>
		<nested:iterate property="document.rootElement.children">
			<tr>
				<td class='category'>
					<nested:define id="category" property="attributes[0].value" /> 
					<html:link styleClass="list" page="/listr" 
 						onclick="set('currCategory', '${category}'); submitForm(); return false"> 
 						<nested:write name="category" /> 
					</html:link> 
				</td>

				<!-- Counting goods quantity for the current category -->
				<c:set var="totalSize" value="0" scope="page" />
				<nested:iterate id="subcategory" property="children">
					<nested:size id="size" property="children" />
					<c:set var="totalSize" value="${totalSize+size}" />
				</nested:iterate>
				<td class="category">${totalSize}</td>
			</tr>
		</nested:iterate>
	</table>
</html:form>