/**
 * 
 */
package org.hyperdata.beeps.parameters;

/**
 * @author danny
 *
 */
public interface ComponentList extends Named, Component, ParameterList {
public void addComponent(Component component);
public void updateParameters(ParameterList parameters);
}
