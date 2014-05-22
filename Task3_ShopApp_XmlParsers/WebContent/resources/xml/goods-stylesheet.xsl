<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	exclude-result-prefixes="ns">
	<xsl:output method='html' />
	<xsl:param name="currCategory" />
	<xsl:param name="currSubcategory" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Goods List</title>
				<link rel='stylesheet' type='text/css' href='./resources/style.css' />
			</head>
			<script type="text/javascript" src="./resources/js/script.js"></script>
			<body>
				<div class='centered'>
					<form id="selectCategory" action="./list" method="post">
						<input type="hidden" id="category" name="category" />
						<input type="hidden" id="subcategory" name="subcategory" />
						<table width='800'>
							<tr>
								<td class='title'>
									<H2>
										<a class="list" href="./list" onclick="list(null, null); return false">
											All Goods</a>
										<xsl:if test="$currCategory">
											&#160;&#160;&#160;/&#160;&#160;&#160;
											<xsl:choose>
												<xsl:when test="$currSubcategory">
													<a class="list" href="./list"
														onclick="list('{$currCategory}', null); return false">
														<xsl:value-of select="$currCategory" />
													</a>
													&#160;&#160;&#160;/&#160;&#160;&#160;
													<xsl:value-of select="$currSubcategory" />
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of select="$currCategory" />
												</xsl:otherwise>
											</xsl:choose>
										</xsl:if>
									</H2>
									<hr />
								</td>
							</tr>
							<tr>
								<td class="title">
									<table class="list">
										<xsl:choose>
											<xsl:when test="$currCategory">
												<xsl:choose>
													<xsl:when test="$currSubcategory">
														<xsl:call-template name="goods" />
													</xsl:when>
													<xsl:otherwise>
														<xsl:call-template name="subcategory" />
													</xsl:otherwise>
												</xsl:choose>
											</xsl:when>
											<xsl:otherwise>
												<xsl:call-template name="category" />
											</xsl:otherwise>
										</xsl:choose>
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
		<tr>
			<th>Category name</th>
			<th>Goods quantity</th>
		</tr>
		<xsl:for-each select="ns:categories/ns:category">
			<tr>
				<td class='category'>
					<xsl:variable name="category">
						<xsl:value-of select="@name" />
					</xsl:variable>
					<a class='list' href='' onclick="list('{$category}'); return false">
						<xsl:value-of select="@name" />
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

	<xsl:template name="subcategory">
		<tr>
			<th>Category name</th>
			<th>Goods quantity</th>
		</tr>
		<xsl:for-each
			select="ns:categories/ns:category[@name=$currCategory]/ns:subcategory">
			<tr>
				<td class='category'>
					<xsl:variable name="subcategory">
						<xsl:value-of select="@name" />
					</xsl:variable>
					<a class="list" href=''
						onclick="list('{$currCategory}', '{$subcategory}'); return false">
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
			select="ns:categories/ns:category/ns:subcategory[@name=$currSubcategory]/ns:goods">
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