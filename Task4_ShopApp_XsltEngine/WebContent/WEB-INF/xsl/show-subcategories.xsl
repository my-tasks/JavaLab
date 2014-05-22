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
							value="goods" />
						<input type="hidden" id="templateId" name="xslt.templateId"
							value="show.goods" />
						<input type="hidden" id="currCategory" name="currCategory"
							value="{$currCategory}" />
						<input type="hidden" id="currSubcategory" name="currSubcategory" />
					</form>
					<table class="main">
						<tr>
							<td class='title'>
								<H2>
									<a class="list" href=""
										onclick="set('templateId', 'show.categories'); submit('form'); return false">
										Main</a>
									&#160;&#160;&#160;/&#160;&#160;&#160;
									<xsl:value-of select="$currCategory" />
								</H2>
								<hr />
							</td>
						</tr>
						<tr>
							<td class="content">
								<table class="list">
									<xsl:call-template name="subcategory" />
									<tr>
										<td colspan="2" class="inline-block">
											<button
												onclick="set('sourceId', 'goods'); set('templateId', 'show.categories'); submit('form'); return false">
												Back
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

	<xsl:template name="subcategory">
		<th>Category name</th>
		<th>Goods quantity</th>
		<xsl:for-each
			select="ns:categories/ns:category[@name=$currCategory]/ns:subcategory">
			<tr>
				<td class='category'>
					<xsl:variable name="subcategory">
						<xsl:value-of select="@name" />
					</xsl:variable>
					<a class="list" href=''
						onclick="set('currSubcategory', '{$subcategory}'); submit('form'); return false">
						<xsl:value-of select="@name" />
					</a>
				</td>
				<td class="category">
					(
					<xsl:value-of select="count(ns:goods)" />
					)
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>