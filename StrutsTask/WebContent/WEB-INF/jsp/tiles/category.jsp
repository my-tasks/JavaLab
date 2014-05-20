<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="list">
	<tr>
		<th><bean:message key="label.category"/></th>
		<th><bean:message key="label.product.quantity"/></th>
	</tr>
	<nested:iterate name="productForm" property="document.rootElement.children" indexId="index">
		<tr>
			<td class='category'>
				<html:link styleClass="list" action="/list.do?method=showSubcategories" 
					paramId="categoryIndex" paramName="index"> 
						<nested:write property="attributes[0].value" /> 
				</html:link> 
			</td>
			<td class="category">
			<!-- Counting goods quantity for the current category -->
				<bean:define id="totalSize" value="0"/>
				<nested:iterate id="subcategory" property="children">
					<nested:size id="size" property="children" />
					<bean:define id="totalSize" value="${totalSize+size}" />
				</nested:iterate>
					<bean:write name="totalSize"/>
			</td>	
		</tr>
	</nested:iterate>
</table>
