<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	exclude-result-prefixes="ns">
	<xsl:import href="add-goods-parameters.xsl" />

	<xsl:template name="css">
		<link rel='stylesheet' type='text/css' href='./resources/style.css' />
	</xsl:template>

	<xsl:template name="jscript">
		<script type='text/javascript' src='./resources/js/script.js'></script>
	</xsl:template>

	<xsl:template name="add">
		<xsl:param name="xslt.safetyKey" />
		<xsl:param name="addCategory" />
		<xsl:param name="addSubcategory" />
		<xsl:param name="isNotEmptyName" />
		<xsl:param name="isNotEmptyProvider" />
		<xsl:param name="isNotEmptyModel" />
		<xsl:param name="isNotEmptyDateOfIssue" />
		<xsl:param name="isNotEmptyPrice" />
		<xsl:param name="isValidModel" />
		<xsl:param name="isValidDate" />
		<xsl:param name="isValidColor" />
		<xsl:param name="isValidPrice" />
		<xsl:param name="isNotInStock" />
		<xsl:param name="addGoodsName" />
		<xsl:param name="addGoodsProvider" />
		<xsl:param name="addGoodsModel" />
		<xsl:param name="addGoodsDateOfIssue" />
		<xsl:param name="addGoodsColor" />
		<xsl:param name="addGoodsPrice" />
		<xsl:param name="addGoodsNotInStock" />
		<th width="400">
			Add goods
			<a onclick="fill(); submit('addGoods')">FILL</a>
		</th>

		<tr class="add-form">
			<td class="add-form">
				<table class="add">
					<form id="addGoods" action="./save.do" method="post">

						<input type="hidden" name="xslt.safetyKey" value="{$xslt.safetyKey}" />
						<input type="hidden" name="xslt.sourceId" value="{$xslt.sourceId}" />
						<input type="hidden" name="xslt.templateId" value="validate.goods" />
						<input type="hidden" name="xslt.requestType" value="xmlUpdate" />
						<input type="hidden" name="xslt.redirectSourceId" value="goods" />
						<input type="hidden" name="xslt.redirectTemplateId" value="show.goods" />
						<input type="hidden" name="currCategory" value="{$addCategory}" />
						<input type="hidden" name="currSubcategory" value="{$addSubcategory}" />
						<tr>
							<td>
							</td>
							<td class="error">
								<xsl:if test="$isNotEmptyName=false">
									Fill the field
								</xsl:if>
							</td>
						</tr>
						<tr>
							<td class="goodsAddLeft" title='Product Name'>
								Product Name:
							</td>
							<td class="goodsAddRight" title='Product Name'>
								<input type="text" id="name" name="addGoodsName" value="{$addGoodsName}" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="error">
								<xsl:if test="$isNotEmptyProvider=false">
									Fill the field
								</xsl:if>
							</td>
						</tr>
						<tr>
							<td class="goodsAddLeft" title='Provider'>
								Provider:
							</td>
							<td class="goodsAddRight" title='Provider'>
								<input type="text" id="provider" name="addGoodsProvider"
									value="{$addGoodsProvider}" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="error">
								<xsl:choose>
									<xsl:when test="$isNotEmptyModel=false">
										Fill the field
									</xsl:when>
									<xsl:when test="$isValidModel=false">
										Incorrect model format
									</xsl:when>
								</xsl:choose>
							</td>
						</tr>
						<tr>
							<td class="goodsAddLeft" title='Model'>
								Model (LLDDD, ex. MF111):
							</td>
							<td class="goodsAddRight" title='Model'>
								<input type="text" id="model" name="addGoodsModel" value="{$addGoodsModel}" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="error">
								<xsl:choose>
									<xsl:when test="$isNotEmptyDateOfIssue=false">
										Fill the field
									</xsl:when>
									<xsl:when test="$isValidDate=false">
										Incorrect date format
									</xsl:when>
								</xsl:choose>
							</td>
						</tr>
						<tr>
							<td class="goodsAddLeft" title='Date of issue'>
								Date of issue (DD-MM-YYYY):
							</td>
							<td class="goodsAddRight" title='Date of issue'>
								<input type="text" id="date" name="addGoodsDateOfIssue"
									value="{$addGoodsDateOfIssue}" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="error">

								<xsl:if test="$isValidColor=false">
									Choose the color
								</xsl:if>
							</td>
						</tr>
						<tr>
							<td class="goodsAddLeft" title='Color'>
								Color:
							</td>
							<td class="goodsAddRight" title='Color'>
								<select name="addGoodsColor" id="color">
									<option value="null" class="select">Choose color</option>
									<option value="white" class="white">white</option>
									<option value="black" label="black" class="black">black</option>
									<option value="brown" id="brown" class="brown">brown</option>
									<option value="gray" class="gray">gray</option>
									<option value="red" class="red">red</option>
									<option value="green" class="green">green</option>
									<option value="blue" class="blue">blue</option>
									<option value="darkblue" class="darkblue">darkblue</option>
									<option value="purple" class="purple">purple</option>
									<option value="pink" class="pink">pink</option>
								</select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="error">
								<xsl:choose>
									<xsl:when test="$isNotEmptyPrice=false and $isNotInStock=false">
										Fill the field
									</xsl:when>
									<xsl:when test="$isValidPrice=false and $isNotInStock=false">
										Incorrect price value
									</xsl:when>
								</xsl:choose>
							</td>
						</tr>
						<tr>
							<td class="goodsAddLeft" title='Price'>
								Price:
							</td>
							<td class="goodsAddRight" title='Price'>
								<input type="text" size="18" name="addGoodsPrice" id="price"
									value="{$addGoodsPrice}" />
								$
							</td>
						</tr>
						<tr>
							<td></td>
							<td class="error"></td>
						</tr>
						<tr>
							<td class="goodsAddLeft" title='Not In Stock'>
								Not In Stock:
							</td>
							<td class="goodsAddRight" title='Not In Stock'>
								<input type="checkbox" id="notInStock" name="addGoodsNotInStock"
									onclick="disablePrice()" />
							</td>
						</tr>
					</form>

				</table>

			</td>
		</tr>

	</xsl:template>

	<xsl:template match="@* | node()" name="save">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>
	<xsl:template
		match="ns:category[@name=$currCategory]/ns:subcategory[@name=$currSubcategory]">
		<xsl:element name="{name()}" namespace="urn:shopapp:goods">
			<xsl:copy-of select="@*" />
			<xsl:apply-templates
				select="//ns:categories/ns:category[@name=$currCategory]/ns:subcategory[@name=$currSubcategory]/ns:goods" />
			<xsl:element name="goods" namespace="urn:shopapp:goods"
				inherit-namespaces="yes">
				<xsl:attribute name="name">
		           <xsl:value-of select="$addGoodsName" />
		        </xsl:attribute>
				<xsl:element name="producer" namespace="urn:shopapp:goods">
					<xsl:value-of select="$addGoodsProvider" />
				</xsl:element>
				<xsl:element name="model" namespace="urn:shopapp:goods">
					<xsl:value-of select="$addGoodsModel" />
				</xsl:element>
				<xsl:element name="date-of-issue" namespace="urn:shopapp:goods">
					<xsl:value-of select="$addGoodsDateOfIssue" />
				</xsl:element>
				<xsl:element name="color" namespace="urn:shopapp:goods">
					<xsl:value-of select="$addGoodsColor" />
				</xsl:element>
				<xsl:choose>
					<xsl:when test="$addGoodsPrice">
						<xsl:element name="price" namespace="urn:shopapp:goods">
							<xsl:value-of select="$addGoodsPrice" />
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="not-in-stock" namespace="urn:shopapp:goods">
							true
						</xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>		