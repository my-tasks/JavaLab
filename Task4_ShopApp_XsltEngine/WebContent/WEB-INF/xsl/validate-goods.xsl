<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	xmlns:java="http://xml.apache.org/xalan/java" exclude-result-prefixes="java">
	<xsl:import href="templates.xsl" />
	<xsl:import href="general-parameters.xsl" />
	<xsl:import href="add-goods-parameters.xsl" />
	<xsl:output method="html" encoding="UTF-8" />
	<xsl:param name="xslt.safetyKey" />

	<xsl:template match="/">
		<xsl:variable name="isNotEmptyCategory"
			select="java:com.epam.xslt.util.FormValidator.isNotEmptyFieldValue($currCategory)" />
		<xsl:variable name="isNotEmptySubcategory"
			select="java:com.epam.xslt.util.FormValidator.isNotEmptyFieldValue($currSubcategory)" />
		<xsl:variable name="isNotEmptyName"
			select="java:com.epam.xslt.util.FormValidator.isNotEmptyFieldValue($addGoodsName)" />
		<xsl:variable name="isNotEmptyProvider"
			select="java:com.epam.xslt.util.FormValidator.isNotEmptyFieldValue($addGoodsProvider)" />
		<xsl:variable name="isNotEmptyModel"
			select="java:com.epam.xslt.util.FormValidator.isNotEmptyFieldValue($addGoodsModel)" />
		<xsl:variable name="isNotEmptyDateOfIssue"
			select="java:com.epam.xslt.util.FormValidator.isNotEmptyFieldValue($addGoodsDateOfIssue)" />
		<xsl:variable name="isValidModel"
			select="java:com.epam.xslt.util.FormValidator.isValidByPattern('model', $addGoodsModel)" />
		<xsl:variable name="isValidDate"
			select="java:com.epam.xslt.util.FormValidator.isValidByPattern('date', $addGoodsDateOfIssue)" />
		<xsl:variable name="isValidColor"
			select="java:com.epam.xslt.util.FormValidator.isValidEnumValue('colors', $addGoodsColor)" />
		<xsl:variable name="isNotInStock"
			select="java:com.epam.xslt.util.FormValidator.isCheckboxChecked($addGoodsNotInStock)" />
		<xsl:variable name="isNotEmptyPrice"
			select="java:com.epam.xslt.util.FormValidator.isNotEmptyFieldValue($addGoodsPrice)" />
		<xsl:variable name="isValidPrice"
			select="java:com.epam.xslt.util.FormValidator.isValidByPattern('price', $addGoodsPrice)" />
		<xsl:variable name="isValidGoods"
			select="$isNotEmptyName and $isNotEmptyProvider and $isNotEmptyCategory and $isNotEmptySubcategory and $isValidModel and $isValidDate and ($isValidPrice or $isNotInStock) and $isValidColor" />
		<xsl:if test="($isValidGoods=false)">
			<xsl:variable name="isValidGoods" />
		</xsl:if>
		<xsl:choose>
			<xsl:when test="$isValidGoods=false">
				<xsl:value-of
					select="java:com.epam.xslt.util.FormValidator.setRequestValidity($isValidGoods)" />
				<html>
					<head>
						<title>Goods List</title>
						<xsl:call-template name="css" />
					</head>
					<xsl:call-template name="jscript" />

					<body>
						<div class='centered'>
							<form id="form" action="./list.do" method="post">
								<input type="hidden" id="sourceId" name="xslt.sourceId"
									value="{$xslt.sourceId}" />
								<input type="hidden" id="templateId" name="xslt.templateId" />
								<input type="hidden" id="currCategory" name="currCategory"
									value="{$currCategory}" />
								<input type="hidden" id="currSubcategory" name="currSubcategory"
									value="{$currSubcategory}" />
							</form>
							<table width='800'>
								<tr>
									<td class='title'>
										<H2>
											<a class="list" href=""
												onclick="set('templateId', 'show.categories'); submit('form'); return false">
												Main
											</a>
											&#160;&#160;&#160;/&#160;&#160;&#160;
											<a class="list" href=""
												onclick="set('templateId', 'show.subcategories'); submit('form'); return false">
												<xsl:value-of select="$currCategory" />
											</a>
											&#160;&#160;&#160;/&#160;&#160;&#160;
											<a class="list" href=""
												onclick="set('templateId', 'show.goods'); submit('form'); return false">
												<xsl:value-of select="$currSubcategory" />
											</a>
											&#160;&#160;&#160;/&#160;&#160;&#160;
											Add goods
										</H2>
										<hr />
									</td>
								</tr>
								<tr>
									<td class="title">
										<table class="list">
											<xsl:call-template name="add">
												<xsl:with-param name="xslt.safetyKey" select="$xslt.safetyKey" />
												<xsl:with-param name="addCategory" select="$currCategory" />
												<xsl:with-param name="addSubcategory" select="$currSubcategory" />
												<xsl:with-param name="isNotEmptyName" select="$isNotEmptyName" />
												<xsl:with-param name="isNotEmptyProvider"
													select="$isNotEmptyProvider" />
												<xsl:with-param name="isNotEmptyModel"
													select="$isNotEmptyModel" />
												<xsl:with-param name="isNotEmptyDateOfIssue"
													select="$isNotEmptyDateOfIssue" />
												<xsl:with-param name="isNotEmptyPrice"
													select="$isNotEmptyPrice" />
												<xsl:with-param name="isValidModel" select="$isValidModel" />
												<xsl:with-param name="isValidDate" select="$isValidDate" />
												<xsl:with-param name="isValidColor" select="$isValidColor" />
												<xsl:with-param name="isValidPrice" select="$isValidPrice" />
												<xsl:with-param name="isNotInStock" select="$isNotInStock" />
												<xsl:with-param name="addGoodsName" select="$addGoodsName" />
												<xsl:with-param name="addGoodsProvider"
													select="$addGoodsProvider" />
												<xsl:with-param name="addGoodsModel" select="$addGoodsModel" />
												<xsl:with-param name="addGoodsDateOfIssue"
													select="$addGoodsDateOfIssue" />
												<xsl:with-param name="addGoodsColor" select="$addGoodsColor" />
												<xsl:with-param name="addGoodsPrice" select="$addGoodsPrice" />
												<xsl:with-param name="addGoodsNotInStock"
													select="$addGoodsNotInStock" />
											</xsl:call-template>
											<tr>
												<td colspan="2" class="inline-block">
													<button
														onclick="set('sourceId', 'goods'); set('templateId', 'show.goods'); 
 					set('currCategory', '{$currCategory}'); set('currSubcategory', '{$currSubcategory}');  
					submit('form'); return false">
														Back
													</button>
													<button onclick="submit('addGoods'); return false">
														Save goods
													</button>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<input type="hidden" id="choosenColor" value='{$addGoodsColor}' />
							<input type="hidden" id="choosenNotInStock" value='{$isNotInStock}' />
						</div>
						<script type="text/javascript">
							window.onload = setColor('color',
							document.getElementById('choosenColor').value);
							setNotInStock('notInStock',
							document.getElementById('choosenNotInStock').value);
						</script>
					</body>
				</html>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of
					select="java:com.epam.xslt.util.FormValidator.setRequestValidity($isValidGoods)" />
				<xsl:call-template name="save" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>