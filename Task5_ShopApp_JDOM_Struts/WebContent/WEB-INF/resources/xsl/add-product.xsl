<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	xmlns:java="http://xml.apache.org/xalan/java" exclude-result-prefixes="java">
	<xsl:output method="xml" encoding="UTF-8" />
	<xsl:param name="categoryName" />
	<xsl:param name="subcategoryName" />
	<xsl:param name="name" />
	<xsl:param name="producer" />
	<xsl:param name="model" />
	<xsl:param name="date-of-issue" />
	<xsl:param name="color" />
	<xsl:param name="price" />
	<xsl:template match="@* | node()" name="save">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="ns:subcategory">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
			<xsl:if test="(../@name=$categoryName) and (@name=$subcategoryName)">
				<xsl:element name="goods" namespace="urn:shopapp:goods">
					<xsl:attribute name="name">
	<xsl:value-of select="$name" />
	</xsl:attribute>
					<xsl:element name="producer" namespace="urn:shopapp:goods">
						<xsl:value-of select="$producer" />
					</xsl:element>
					<xsl:element name="model" namespace="urn:shopapp:goods">
						<xsl:value-of select="$model" />
					</xsl:element>
					<xsl:element name="date-of-issue" namespace="urn:shopapp:goods">
						<xsl:value-of select="$date-of-issue" />
					</xsl:element>
					<xsl:element name="color" namespace="urn:shopapp:goods">
						<xsl:value-of select="$color" />
					</xsl:element>
					<xsl:choose>
						<xsl:when test="$price">
							<xsl:element name="price" namespace="urn:shopapp:goods">
								<xsl:value-of select="$price" />
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<xsl:element name="not-in-stock" namespace="urn:shopapp:goods">
								true
							</xsl:element>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:element>
			</xsl:if>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
