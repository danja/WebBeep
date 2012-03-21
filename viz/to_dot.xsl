<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:res="http://www.w3.org/2005/sparql-results#"
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:xhtml="http://www.w3.org/1999/xhtml" 
  exclude-result-prefixes="res xsl rdf xhtml">

  <xsl:output method="text" encoding="UTF-8" />

  <xsl:template match="/res:sparql">
digraph G {
	<xsl:apply-templates select="res:results" />
}
  </xsl:template>

<!--  subject -&gt; object [label="property"]; -->

  <xsl:template match="/res:sparql/res:results">

  <xsl:for-each select="res:result">
       <xsl:value-of select="res:binding[@name='sLabel']/res:literal" /> 
          -&gt; <xsl:value-of select="res:binding[@name='oLabel']/res:literal" /> 
       [label="<xsl:value-of select="res:binding[@name='pLabel']/res:literal" />", fontsize=12];
  </xsl:for-each>
</xsl:template>

<xsl:template match="text()"/>
</xsl:stylesheet>

