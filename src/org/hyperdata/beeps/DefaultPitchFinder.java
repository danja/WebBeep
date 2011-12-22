/**
 * 
 */
package org.hyperdata.beeps;

import java.util.List;

import org.hyperdata.beeps.util.Tone;
import org.hyperdata.go.parameters.DefaultParameterized;

/**
 * @author danny
 *
 */
public abstract class DefaultPitchFinder extends DefaultParameterized implements PitchFinderGeneral {

	public DefaultPitchFinder(String name){
		super(name);
	}
}
