/**
 * 
 */
package org.hyperdata.beeps.parameters;

import java.util.Set;



/**
 * @author danny
 *
 */
public interface Parameterized {

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setParameter(String name, Object value);

	public abstract Object getParameter(String name);

	public abstract void initFromParameters();

	// public void createParameters();
	/**
	 * @return
	 */
	public abstract Set<String> parameterNames();

	/**
	 * @param parameter
	 */
	public abstract void setParameter(Parameter parameter);

}