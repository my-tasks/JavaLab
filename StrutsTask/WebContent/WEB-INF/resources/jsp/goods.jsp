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
	<html:hidden property="currCategory" styleId="currCategory" value="${goodsForm.currCategory}"/>
	<html:hidden property="currSubcategory" styleId="currSubcategory"  value="${goodsForm.currSubcategory}"/>
	
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
			<nested:equal value="${goodsForm.currCategory}" property="attributes[0].value">
				<nested:iterate property="children">
					<nested:equal value="${goodsForm.currSubcategory}" property="attributes[0].value">
						<nested:iterate property="children">
							<tr>
								<td class="goods" title='Product Name'>
									<nested:text property="attributes[0].value" size="10" />
								</td>
								<td class="goods" title='Provider'>
									<nested:text property="child(producer).text" size="10" />
								</td>
								<td class="goods" title='Model'>
									<nested:text property="child(model).text" size="10" />
								</td>
								<td class="goods" title='Date of issue'>
									<nested:text property="child(date-of-issue).text" size="10" />
								</td>
								<td class="goods" title='Color'>
									<nested:text property="child(color).text" size="10" />
								</td>
								<nested:empty property="child(price)">
									<td class="goods" title='Not In Stock'>
									<div class="not-in-stock">
										NOT IN STOCK
									</div>
									</td>
								</nested:empty>
								<nested:notEmpty property="child(price)">
									<td class="goods" title='Price'>
										<nested:text property="child(price).text" size="10" />
									</td>
								</nested:notEmpty>
								<td class="goods">
									<nested:checkbox property="child(not-in-stock)" />
								</td>
							</tr>
						</nested:iterate>
					</nested:equal>
				</nested:iterate>
			</nested:equal>
		</nested:iterate>
	</table>
	</tr>
	<tr>
		<td class="inline-block">
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
</html:form>
