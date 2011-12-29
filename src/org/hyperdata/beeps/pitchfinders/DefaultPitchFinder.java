/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.List;

import org.hyperdata.beeps.system.DefaultParameterList;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public abstract class DefaultPitchFinder extends DefaultParameterList implements PitchFinderGeneral {

	public DefaultPitchFinder(String name){
		super(name);
	}
}
