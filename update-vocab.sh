
# work on a local copy
# curl -o proc.ttl http://hyperdata.org/xmlns/proc/index.ttl

# is needed?
rapper -i turtle proc.ttl -o rdfxml > proc.rdf

# build classpath ###
# change to ';' for Cygwin
SEP=':'
# collect all the jar names
for jar in lib/**/*.jar
  do
  # Check for no expansion
  [ -e "$jar" ] || break
  #echo "Path: $jar"
  [ "$CP" != "" ] && CP="${CP}${SEP}"
  CP="${CP}$jar"
done
CP=$CP:classes
#####################

# make schema.java
java -cp $CP  jena.schemagen -i proc.rdf  -a http://purl.org/stuff/proc/ --package org.hyperdata.proc -o src/