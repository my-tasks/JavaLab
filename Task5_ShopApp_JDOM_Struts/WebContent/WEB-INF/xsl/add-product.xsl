<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	exclude-result-prefixes="#default ns">
	<xsl:output method="xml" encoding="UTF-8" />
	<xsl:param name="categoryName" />
	<xsl:param name="subcategoryName" />
	<xsl:param name="name" />
	<xsl:param name="producer" />
	<xsl:param name="model" />
	<xsl:param name="date-of-issue" />
	<xsl:param name="color" />
	<xsl:param name="price" />
	<xsl:template match="@* | node()">
		<xsl:copy copy-namespaces="yes">
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template
		match="//ns:categories/ns:category[@name=$categoryName]/ns:subcategory[@name=$subcategoryName]">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
			<goods xmlns="urn:shopapp:goods">
				<xsl:attribute name="name">
						<xsl:value-of select="$name" />
					</xsl:attribute>
				<xsl:element name="producer">
					<xsl:value-of select="$producer" />
				</xsl:element>
				<xsl:element name="model">
					<xsl:value-of select="$model" />
				</xsl:element>
				<xsl:element name="date-of-issue">
					<xsl:value-of select="$date-of-issue" />
				</xsl:element>
				<xsl:element name="color">
					<xsl:value-of select="$color" />
				</xsl:element>
				<xsl:choose>
					<xsl:when test="$price">
						<xsl:element name="price">
							<xsl:value-of select="$price" />
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="not-in-stock">
							true
						</xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</goods>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
