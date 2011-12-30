/**
 * 
 */
package org.hyperdata.common;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author danny
 * 
 */
public abstract class Describer implements Described {

//	private String description = "A utility to generate descriptions from Java class instances";
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.hyperdata.Described#getDescription()
//	 */
//	@Override
//	public abstract String getDescription() {
//		return description;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hyperdata.Described#describe()
	 */
	@Override
	public String describe() {
		return defaultDescribe(this);
	}
	
	//Set<String> variables = new HashSet<String>();
	
	public void describeVariable(Object object, String variableName){
		try {
			Field field = object.getClass().getDeclaredField(variableName);
			System.out.println("field "+variableName+" = "+field.get(object));
		} catch (Exception exception) {
			exception.printStackTrace();
		} 
	}
	
	
	

	/**
	 * e.g. org.hyperdata.common.Describer =>
	 * http://hyperdata.org/projects/common/code/org/hyperdata/common/Describer
	 * 
	 * @param object
	 * @return
	 */
	public static String extractClassURI(Object object) {
		String javaName = object.getClass().getCanonicalName();
		System.out.println(javaName);
		String[] split = javaName.split("\\.");
		System.out.println(split.length);
		String uri = "http://";
		uri += split[1] + "." + split[0];
		uri += "/projects/" + split[2] + "/code";
		for (int i = 0; i < split.length; i++) {
			uri += "/" + split[i];
		}
		return uri;
	}
	
	public static String turtleFromVariable(String name, Object variable){
		String turtle = "# variable";
		if(variable instanceof Double){
			turtle += "Double";
		}
		return turtle;
	}

	public static String getNamespaces() {
		String namespaces = ""
				+ "@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
				+ "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n"
				+ "@prefix owl:     <http://www.w3.org/2002/07/owl#> .\n"
				+ "@prefix xsd:     <http://www.w3.org/2001/XMLSchema#> .\n"
				+ "@prefix dc:      <http://purl.org/dc/terms/> .\n"
				+ "@prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n"
				+ "@prefix stuff:       <http://purl.org/stuff/> .\n\n";
		return namespaces;
	}

	public static String defaultDescribe(Described described) {
		String description = getNamespaces();
		description += "<" + extractClassURI(described)
				+ "> a <http://purl.org/stuff/java/Class> ;\n";
		description += "   foaf:name \"" + described.getClass().getSimpleName()
				+ "\" ; \n";
		return description;
	}
}
