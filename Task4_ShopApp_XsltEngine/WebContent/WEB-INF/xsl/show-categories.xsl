<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	exclude-result-prefixes="ns">
	<xsl:import href="templates.xsl" />
	<xsl:import href="general-parameters.xsl" />
	<xsl:output method='html' />
	<xsl:template match="/">
		<xsl:variable name="temp" />
		<xsl:variable name="category">
			<xsl:value-of select="ns:categories/ns:category[1]/@name" />
		</xsl:variable>
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
							value="show.subcategories" />
						<input type="hidden" id="currCategory" name="currCategory" />
						<table class="main">
							<tr>
								<td class='title'>
									<H2>
										<a class="list" href="./list.do">
											Main
										</a>
									</H2>
									<hr />
								</td>
							</tr>
							<tr>
								<td class="content">
									<table class="list">
										<xsl:call-template name="category" />
									</table>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template name="category">
		<th>Category name</th>
		<th>Goods quantity</th>
		<xsl:for-each select="ns:categories/ns:category">
			<tr>
				<td class='category'>
					<xsl:variable name="category">
						<xsl:value-of select="@name" />
					</xsl:variable>
					<a class='list' href='./list.do'
						onclick="set('currCategory', '{$category}'); submit('form'); return false">
						<xsl:value-of select="$category" />
					</a>
				</td>
				<td class="category">
					(
					<xsl:value-of select="count(ns:subcategory/ns:goods)" />
					)
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>