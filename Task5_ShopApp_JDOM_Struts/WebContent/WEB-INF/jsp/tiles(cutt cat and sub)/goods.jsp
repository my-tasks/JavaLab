<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/list" method="post">
	<html:hidden property="method" styleId="method" value="update"/>
	<html:hidden property="currCategory" styleId="currCategory" value="${productForm.currCategory}"/>
	<html:hidden property="currSubcategory" styleId="currSubcategory"  value="${productForm.currSubcategory}"/>
	
	<table class="list">
		<tr>
			<th>Product name</th>
			<th>Producer</th>
			<th>Model</th>
			<th>Date of issue</th>
			<th>Color</th>
			<th>Price</th>
			<th>Not in stock</th>
		</tr>
		<nested:iterate property="document.rootElement.children">
			<nested:equal value="${productForm.currCategory}" property="attributes[0].value">
				<nested:iterate property="children">
					<nested:equal value="${productForm.currSubcategory}" property="attributes[0].value">
						<nested:iterate property="children" indexId="ind">
							<tr>
								<td class="goods" title='Product Name'>
									<nested:text property="attributes[0].value" size="10" styleId="name${ind}"/>
								</td>
								<td class="goods" title='Provider'>
									<nested:text property="child(producer).text" size="10" styleId="provider${ind}"/>
								</td>
								<td class="goods" title='Model'>
									<nested:text indexed="true"  property="child(model).text" size="10" styleId="model${ind}"/>
								</td>
								<td class="goods" title='Date of issue'>
									<nested:text indexed="true"  property="child(date-of-issue).text" size="10" styleId="date${ind}"/>
								</td>
								<td class="goods" title='Color' styleId="priceTD${ind}">
									<nested:text indexed="true" property="child(color).text" size="10" styleId="price${ind}"/>
								</td>
								<nested:empty property="child(price)">
								<c:set var="checked" value="on" scope="page"/>
									<td class="goods" title='Not In Stock' id="priceTD${ind}">
									<div class="not-in-stock">
										NOT IN STOCK
									</div>
									</td>
								</nested:empty>
								<nested:notEmpty property="child(price)">
<%-- 									<c:set var="checked" value="false"/> --%>
									<td class="goods" title='Price' id="priceTD${ind}">
										<nested:text indexed="true" property="child(price).text" size="10" />
									</td>
								</nested:notEmpty>
								<td class="goods">
									<nested:checkbox property="child(not-in-stock)" value="${checked}" onclick="changePriceTD(${ind})" styleId="cbox${ind}"/>
								</td>
							</tr>
						</nested:iterate>
					</nested:equal>
				</nested:iterate>
			</nested:equal>
		</nested:iterate>
		<tr>
			<td colspan="7" class="inline-block" >
				<button	onclick="set('method', 'showSubcategories'); submitForm(); return false">
					Back
				</button>
				<html:submit styleClass="button">
					Save changes
				</html:submit>
				<button onclick="submit('addForm'); return false">
					Add	goods
				</button>
			</td>
		</tr>
	</table>
</html:form>
