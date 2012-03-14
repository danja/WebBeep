/**
 * 
 */
package org.hyperdata.beeps.util;

import org.hyperdata.common.describe.DefaultDescriber;
import org.hyperdata.common.describe.Described;
import org.hyperdata.common.describe.Describer;
import org.hyperdata.common.describe.ExampleUnrelatedClass;
import org.hyperdata.common.describe.Named;
import org.hyperdata.common.describe.TestDescriber;


/**
 * Example of using dork - here for reference
 * 
 * org.hyperdata.common.describe.*
 * 
 * https://github.com/danja/dork
 * 
 * @author danny
 *
 */
public class DorkExample extends TestDescriber implements Described, Named {

	public String describe(){
		String description = DefaultDescriber.getDescription(this);
		String uri = DefaultDescriber.getURI(this);
		description += "<"+uri+"> dc:description \"An example program\" .\n";
		return description;
	}
	
	public static void main(String[] args){
		DorkExample example = new DorkExample();
		TestDescriber td = new TestDescriber();
		
		// namespace prefixes as header
		String description = Describer.namespaces;
		
		// gives properties labels
		description += Describer.vocab;
		
		// example has a describe method, so call it
		description += example.describe();
		
		// get td's description by introspection
		description += DefaultDescriber.getDescription(td);
		
		// get unrelated class's description by introspection
		description += DefaultDescriber.getDescription(ExampleUnrelatedClass.class);
		System.out.println(description);
		DefaultDescriber.save("./example.ttl", description);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Named#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.system.Named#getName()
	 */
	@Override
	public String getName() {
		return "This Example";
	}
}
