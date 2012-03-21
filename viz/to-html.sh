#!/bin/sh
#
# dork (Description of Runtime Klasses) demo
#
# demo depends on:
#     (Java)
#     Redland (rasqal/roqet) - http://librdf.org/rasqal/roqet.html
#     xsltproc - http://xmlsoft.org/XSLT/xsltproc2.html
#
# both are available for recent Ubuntu via synaptic
#
# https://github.com/danja/dork
# danja 2012-01-19
#


echo running SPARQL query against the RDF...
roqet -q -r xml to_html.rq -D ../beepy.ttl > beepy.sr
echo ok.

echo transforming query results into dot format...
xsltproc result-to-html.xsl beepy.sr > beepy.html
echo ok.



