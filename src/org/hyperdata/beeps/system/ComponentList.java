/**
 * 
 */
package org.hyperdata.beeps.system;

import org.hyperdata.common.describe.Described;
import org.hyperdata.common.describe.Named;


/**
 * @author danny
 *
 */
public interface ComponentList extends Named, Described, Parameterized {
public void addComponent(Component component);
public void updateParameters(ParameterList parameters);
public int size();
/**
 * @return
 */
public ParameterList getParameters();
/**
 * @param parameters
 */
void update(ParameterList parameters);
}
