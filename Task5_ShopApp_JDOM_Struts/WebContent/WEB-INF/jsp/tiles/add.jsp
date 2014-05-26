<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/add" method="post" onsubmit="return validateProductForm()">
	<html:hidden property="method" styleId="method" value="saveProduct" />
	<html:hidden property="categoryIndex" value="${productForm.categoryIndex}"/>
	<html:hidden property="subcategoryIndex" value="${productForm.subcategoryIndex}"/>
<nested:nest property="newProduct">
	<table class="list">
		<tr>
			<td class="content">
				<div class="add-form">
					<table class="add-form">
						<tr width="400">
							<th><bean:message key="label.add.product" /></th>
							<th><a onclick="fill(); submit('addGoods')">FILL</a></th>
						</tr>
						<tr class="add-form">
							<td colspan="2" class="add-form">
								<table class="add">
									<nested:hidden property="category" value="${productForm.categoryName}" />
									<nested:hidden property="subcategory" value="${productForm.subcategoryName}" />
									<!--Product Name field-->
									<tr>
										<td></td>
										<td class="error" id="err_name"><html:errors property="name" /></td>
									</tr>
									<tr>
										<td class="goodsAddLeft" title="<bean:message key='product.name'/>">
											<bean:message key='product.name' />:
										</td>
										<td class="goodsAddRight" title="<bean:message key='product.name'/>">
											<nested:text property="name" styleId="name"/>
										</td>
									</tr>
									<!--Product Producer field-->
									<tr>
										<td></td>
										<td class="error" id="err_producer"><html:errors property="producer" /></td>
									</tr>
									<tr>
										<td class="goodsAddLeft" title="<bean:message key='product.producer'/>">
											<bean:message key='product.producer' />:
										</td>
										<td class="goodsAddRight" title="<bean:message key='product.producer'/>">
											<nested:text property="producer" styleId="producer"/>
										</td>
									</tr>
									<!--Product Model field-->
									<tr>
										<td></td>
										<td class="error" id="err_model"><html:errors property="model" /></td>
									</tr>
									<tr>
										<td class="goodsAddLeft" title="<bean:message key='product.model'/>">
											<bean:message key='product.model' /> (<bean:message key='pattern.model' />):
										</td>
										<td class="goodsAddRight" title="<bean:message key='product.model'/>">
											<nested:text property="model" styleId="model"/>
										</td>
									</tr>
									<!--Product Date-Of-Issue field-->
									<tr>
										<td></td>
										<td class="error" id="err_date"><html:errors property="date" /></td>
									</tr>
									<tr>
										<td class="goodsAddLeft" title="<bean:message key='product.date'/>">
											<bean:message key='product.date' /> (<bean:message key='pattern.date' />):
										</td>
										<td class="goodsAddRight" title="<bean:message key='product.date'/>">
											<nested:text property="dateOfIssue" styleId="date"/>
										</td>
									</tr>
									<!--Product Color field-->
									<tr>
										<td></td>
										<td class="error" id="err_color"><html:errors property="color" /></td>
									</tr>
									<tr>
										<td class="goodsAddLeft" title="<bean:message key='product.color'/>">
											<bean:message key='product.color' />
										</td>
										<td class="goodsAddRight" title="<bean:message key='product.color'/>">
											<nested:select property="color" styleId="color">
												<html:option value="" styleClass="select">Choose color</html:option>
												<html:option value="white" styleClass="white">white</html:option>
												<html:option value="black" styleClass="black">black</html:option>
												<html:option value="brown" styleClass="brown">brown</html:option>
												<html:option value="gray" styleClass="gray">gray</html:option>
												<html:option value="red" styleClass="red">red</html:option>
												<html:option value="green" styleClass="green">green</html:option>
												<html:option value="blue" styleClass="blue">blue</html:option>
												<html:option value="darkblue" styleClass="darkblue">darkblue</html:option>
												<html:option value="purple" styleClass="purple">purple</html:option>
												<html:option value="pink" styleClass="pink">pink</html:option>
											</nested:select></td>
									</tr>
									<!--Product Price field-->
									<tr>
										<td></td>
										<td class="error" id="err_price"><html:errors property="price" /></td>
									</tr>
									<tr>
										<td class="goodsAddLeft" title="<bean:message key='product.price'/>">
											<bean:message key='product.price' />:
										</td>
										<td class="goodsAddRight" title="<bean:message key='product.price'/>">
											<nested:text property="price" styleId="price"/>
										</td>
									</tr>
									<!--Product Not-In-Stock field-->
									<tr>
										<td></td>
										<td class="error"><html:errors property="notInStock" /></td>
									</tr>
									<tr>
										<td class="goodsAddLeft"
											title="<bean:message key='product.not.in.stock'/>"><bean:message
												key='product.not.in.stock' />:</td>
										<td class="goodsAddRight" title="<bean:message key='product.price'/>">
											<nested:checkbox property="notInStock" styleId="notInStock" onclick="disablePrice()" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="inline-block">
				<button onclick="document.location='./list.do?method=showProducts&categoryIndex=${productForm.categoryIndex}&subcategoryIndex=${productForm.subcategoryIndex}'; return false">
					<bean:message key='button.back' />
				</button> 
				<html:submit styleClass="button">
					<bean:message key='button.add' />
				</html:submit>
			</td>
		</tr>
	</table>
</nested:nest>
</html:form>
