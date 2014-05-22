<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	exclude-result-prefixes="ns">
	<xsl:import href="templates.xsl" />
	<xsl:import href="general-parameters.xsl" />
	<xsl:output method='html' />
	<xsl:template match="/">
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
						<input type="hidden" id="templateId" name="xslt.templateId"
							value="add.goods" />
						<input type="hidden" id="currCategory" name="currCategory"
							value="{$currCategory}" />
						<input type="hidden" id="currSubcategory" name="currSubcategory"
							value="{$currSubcategory}" />
					</form>
					<table class="main">
						<tr>
							<td class='title'>
								<H2>
									<a class="list" href=""
										onclick="set('templateId', 'show.categories'); submit('form'); return false">
										Main</a>
									&#160;&#160;&#160;/&#160;&#160;&#160;
									<a class="list" href="./list.do"
										onclick="set('templateId', 'show.subcategories'); submit('form'); return false">
										<xsl:value-of select="$currCategory" />
									</a>
									&#160;&#160;&#160;/&#160;&#160;&#160;
									<xsl:value-of select="$currSubcategory" />
								</H2>
								<hr />
							</td>
						</tr>
						<tr>
							<td class="content">
								<table class="list">
									<xsl:call-template name="goods" />
									<tr>
										<td colspan="6" class="inline-block">
											<form id="addForm" action="./add.do" method="post">
												<input type="hidden" name="xslt.safeRequest" value="true" />
												<input type="hidden" name="xslt.sourceId" value="goods" />
												<input type="hidden" name="xslt.templateId" value="add.goods" />
												<input type="hidden" name="currCategory" value="{$currCategory}" />
												<input type="hidden" name="currSubcategory" value="{$currSubcategory}" />
											</form>
											<button
												onclick="set('sourceId', 'goods'); set('templateId', 'show.subcategories'); set('currCategory', '{$currCategory}'); submit('form'); return false">
												Back
											</button>
											<button onclick="submit('addForm'); return false">
												Add goods
											</button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template name="goods">
		<TR>
			<th>Product name</th>
			<th>Producer</th>
			<th>Model</th>
			<th>Date of issue</th>
			<th>Color</th>
			<th>Price</th>
		</TR>
		<xsl:for-each
			select="ns:categories/ns:category[@name=$currCategory]/ns:subcategory[@name=$currSubcategory]/ns:goods">
			<TR>
				<TD class="goods" title='Product Name'>
					<xsl:value-of select="@name" />
				</TD>
				<TD class="goods" title='Provider'>
					<xsl:value-of select="ns:producer" />
				</TD>
				<TD class="goods" title='Model'>
					<xsl:value-of select="ns:model" />
				</TD>
				<TD class="goods" title='Date of issue'>
					<xsl:value-of select="ns:date-of-issue" />
				</TD>
				<TD class="goods" title='Color'>
					<xsl:variable name="color">
						<xsl:value-of select="ns:color" />
					</xsl:variable>
					<DIV id='square' style='background: {$color}' />
				</TD>
				<xsl:choose>
					<xsl:when test="ns:price">
						<TD class="goods" title='Price'>
							<xsl:value-of select="ns:price" />
							$
						</TD>
					</xsl:when>
					<xsl:otherwise>
						<TD class="goods">
							<DIV title='Not In Stock' class='not-in-stock'>NOT IN STOCK</DIV>
						</TD>
					</xsl:otherwise>
				</xsl:choose>
			</TR>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
