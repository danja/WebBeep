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
	 * @return human-readable description
	 */
	public String getDescription();

	/**
	 * @return machine-readable (Turtle/RDF) description
	 */
	public String describe();
}
