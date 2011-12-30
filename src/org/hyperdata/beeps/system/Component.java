/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.Described;

/**
 * @author danny
 * 
 */
public interface Component extends ParameterList, Described {
	public void initFromParameters();
}
