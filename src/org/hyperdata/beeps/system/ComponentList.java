/**
 * 
 */
package org.hyperdata.beeps.system;


/**
 * @author danny
 *
 */
public interface ComponentList extends Named, Component, ParameterList {
public void addComponent(Component component);
public void updateParameters(ParameterList parameters);
}
