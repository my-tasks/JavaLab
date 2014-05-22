<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns="urn:shopapp:goods"
	exclude-result-prefixes="ns" xmlns:java="http://xml.apache.org/xalan/java">
	<xsl:import href="templates.xsl" />
	<xsl:import href="general-parameters.xsl" />
	<xsl:param name="xslt.safetyKey" />
	<xsl:output method='html' />
	<xsl:template match="/" name="add-goods">
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
					<table class="main">
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
							<td class="content">
								<div class="add-form">
									<table class="add-form">
										<xsl:call-template name="add">
											<xsl:with-param name="xslt.safetyKey" select="$xslt.safetyKey" />
											<xsl:with-param name="addCategory" select="$currCategory" />
											<xsl:with-param name="addSubcategory" select="$currSubcategory" />
											<xsl:with-param name="isNotEmptyName">
												true
											</xsl:with-param>
											<xsl:with-param name="isNotEmptyModel">
												true
											</xsl:with-param>
											<xsl:with-param name="isNotEmptyDateOfIssue">
												true
											</xsl:with-param>
											<xsl:with-param name="isNotEmptyPrice">
												true
											</xsl:with-param>
											<xsl:with-param name="isValidModel">
												true
											</xsl:with-param>
											<xsl:with-param name="isValidDate">
												true
											</xsl:with-param>
											<xsl:with-param name="isValidColor">
												true
											</xsl:with-param>
											<xsl:with-param name="isValidPrice">
												true
											</xsl:with-param>
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
								</div>
							</td>
						</tr>
					</table>
				</div>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>