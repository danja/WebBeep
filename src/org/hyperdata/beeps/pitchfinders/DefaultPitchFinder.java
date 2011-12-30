/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.List;

import org.hyperdata.beeps.system.DefaultComponent;
import org.hyperdata.beeps.system.DefaultParameterList;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 *
 */
public abstract class DefaultPitchFinder extends DefaultComponent implements PitchFinderGeneral {

	public DefaultPitchFinder(String name){
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.common.Described#describe()
	 */
	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return null;
	}
}
