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

# echo generating the demo RDF...
# java -cp dork.jar org.hyperdata.common.describe.Example > example/example.ttl
# echo ok.

echo running SPARQL query against the RDF...
roqet -q  to_dot.rq -D ../beepy.ttl > beepy.sr
echo ok.

echo transforming query results into dot format...
xsltproc to_dot.xsl beepy.sr > beepy.dot
echo ok.

echo run spring-layout on dot data and produce image...
neato -Goverlap=scale -Tpng beepy.dot > beepy.png
echo done.
echo Result is beepy.png

