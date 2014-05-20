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
	<html:hidden property="categoryIndex" styleId="categoryIndex" value="${productForm.categoryIndex}"/>
	<html:hidden property="subcategoryIndex" styleId="subcategoryIndex"  value="${productForm.subcategoryIndex}"/>
	<nested:root name="productForm">
	<nested:nest property="document">
	<nested:nest property="rootElement">
	<nested:nest property="children[${productForm.categoryIndex}]">
	<table class="list">
		<tr>
			<th><bean:message key="product.name"/></th>
			<th><bean:message key="product.producer"/></th>
			<th><bean:message key="product.model"/></th>
			<th><bean:message key="product.date"/></th>
			<th><bean:message key="product.color"/></th>
			<th><bean:message key="product.price"/></th>
			<th><bean:message key="product.not.in.stock"/></th>
		</tr>
		<nested:iterate property="children[${productForm.subcategoryIndex}].children" indexId="ind">
			<tr>
				<td class="goods" title="<bean:message key='product.name'/>">
					<nested:text indexed="true" property="attributes[0].value" size="10" styleId="name${ind}"/>
				</td>
				<td class="goods" title="<bean:message key='product.producer'/>">
					<nested:text indexed="true" property="child(producer).text" size="10" styleId="provider${ind}"/>
				</td>
				<td class="goods" title="<bean:message key='product.model'/>">
					<nested:text indexed="true" property="child(model).text" size="10" styleId="model${ind}"/>
				</td>
				<td class="goods" title="<bean:message key='product.date'/>">
					<nested:text indexed="true" property="child(date-of-issue).text" size="10" styleId="date${ind}"/>
				</td>
				<td class="goods" title="<bean:message key='product.color'/>">
					<nested:text indexed="true" property="child(color).text" size="10" styleId="price${ind}"/>
				</td>
				<nested:empty property="child(price)">
					<td class="price" title="<bean:message key='product.not.in.stock'/>" id="priceTD${ind}">
					<div class="not-in-stock">
							NOT IN STOCK
					</div>
					</td>
				</nested:empty>
				<nested:notEmpty property="child(price)">
					<td class="price" title='<bean:message key='product.price'/>' id="priceTD${ind}">
						<nested:text indexed="true" property="child(price).text" size="10" />
					</td>
				</nested:notEmpty>
				<nested:empty property="child(price)">
					<td class="notInStock">
						<input type="checkbox" checked="checked" onclick="changePriceTD(${ind})" id="cbox${ind}"/>
<%-- 						<nested:checkbox property="child(not-in-stock)" value="true" onclick="changePriceTD(${ind})" styleId="cbox${ind}"/> --%>
					</td>
				</nested:empty>
				<nested:notEmpty property="child(price)">
					<td class="notInStock">
						<input type="checkbox" onclick="changePriceTD(${ind})" id="cbox${ind}"/>
<%-- 						<nested:checkbox property="child(not-in-stock)"  value="true" onclick="changePriceTD(${ind})" styleId="cbox${ind}"/> --%>
					</td>
				</nested:notEmpty>
			</tr>
		</nested:iterate>
		<tr>
			<td colspan="7" class="inline-block" >
				<button	onclick="document.location='./list.do?method=showSubcategories&categoryIndex=${productForm.categoryIndex}'; return false">
					<bean:message key='button.back'/>
				</button>
				<html:submit styleClass="button">
					<bean:message key='button.save'/>
				</html:submit>
				<html:button styleClass="button" property="" onclick="document.location='./update.do?method=addProduct&categoryIndex=${productForm.categoryIndex}&subcategoryIndex=${productForm.subcategoryIndex}'">
					<bean:message key='button.add'/>
				</html:button>
			</td>
		</tr>
	</table>
	</nested:nest>
	</nested:nest>
	</nested:nest>
	</nested:root>
</html:form>
