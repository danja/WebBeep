/**
 * 
 */
package org.hyperdata.proc;

import java.io.InputStream;
import java.util.List;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * @author danny
 * 
 */
public class BeepBuilder {

	private Model model;

	private static int XSD_LENGTH = "http://www.w3.org/2001/XMLSchema#".length();
	
	public BeepBuilder(Model model) {
		this.model = model;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String inputFileName = "beepy.ttl";

		Model model = ModelFactory.createDefaultModel();
		FileManager.get().readModel(model, inputFileName,
				"http://purl.org/stuff/beep/", "Turtle");

		BeepBuilder builder = new BeepBuilder(model);
		builder.buildSystem();
	}

	/**
	 * 
	 */
	private void buildSystem() {

		ResIterator systems = model.listResourcesWithProperty(RDF.type,
				Proc.System);

		while (systems.hasNext()) {
			Resource system = systems.nextResource();
			tab(getLabel(system));
			buildPipeline(system);
		}
	}

	
	public String getLabel(Resource resource){
		try {
			return resource.getProperty(RDFS.label).getObject()
					.toString();
		} catch (Exception e) {
			return "<" + resource + ">";
		}
	}
	
	private void tabs(){
		for(int j=0;j<depth;j++){
			System.out.print("\t");
		}
	}
	
	public void tab(String string){
		tabs();
		System.out.println(string);
	}

	int depth = 0;
	/**
	 * @param system
	 */
	private void buildPipeline(Resource parent) {
		if(!parent.hasProperty(Proc.components)) return; // is empty
		depth++;

		RDFNode componentsNode = parent.getProperty(Proc.components)
				.getObject();
		List<RDFNode> components = componentsNode.as(RDFList.class)
				.asJavaList();
		for (int i = 0; i < components.size(); i++) {
			Resource component = (Resource) components.get(i);
			// tabs();
			tab(getLabel(component));
			if(component.hasProperty(RDF.type, Proc.Pipeline)){
				buildPipeline(component);
			}
			if(component.hasProperty(Proc.parameter)){
				buildParameters(component);
			}
		}
		depth = 1;
	}

	/**
	 * @param component
	 */
	private void buildParameters(Resource component) {
		StmtIterator iterator = component.listProperties(Proc.parameter);
		while(iterator.hasNext()){
			Resource parameter = (Resource)iterator.next().getObject();
			System.out.print("\t");
			
			Statement valueStmt = parameter.getProperty(RDF.value);
			String name = getLabel(parameter);
			String value = valueStmt.getString();
			
			String type = valueStmt.getLiteral().getDatatypeURI();
			if(type != null){
				type = type.substring(XSD_LENGTH);
			}else {
				type = "string";
			}
			
	//		System.out.print("\t");
			tab(name + " = "+value+" ["+type+"]");
		}
	}
}
