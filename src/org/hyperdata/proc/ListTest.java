/**
 * 
 */
package org.hyperdata.proc;




import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author danny
 *
 */
public class ListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel();
		
Resource r1 = model.createResource("http://example.org/1");
Resource r2 = model.createResource("http://example.org/2");

RDFNode[] nodes = {r1, r2};
RDFList list = model.createList(nodes);

Resource s = model.createResource("http://example.org/S");
Property p = model.createProperty("http://example.org/P");
model.add(s,p,list);
model.write(System.out, "Turtle");
	}

}
