/**
 * 
 */
package org.hyperdata.common;

/**
 * Embeds self-descriptions in classes
 * 
 * @author danny
 * 
 */
public interface Described {

	/**
	 * @return machine-readable (Turtle/RDF) description
	 */
	public String describe();
}
