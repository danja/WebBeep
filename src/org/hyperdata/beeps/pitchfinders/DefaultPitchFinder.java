/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.List;

import org.hyperdata.beeps.parameters.DefaultParameterized;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public abstract class DefaultPitchFinder extends DefaultParameterized implements PitchFinderGeneral {

	public DefaultPitchFinder(String name){
		super(name);
	}
}
