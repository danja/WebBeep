/**
 * 
 */
package org.hyperdata.beeps.pitchfinders;

import java.util.List;

import org.hyperdata.beeps.system.DefaultComponent;
import org.hyperdata.beeps.system.DefaultParameterList;
import org.hyperdata.beeps.util.Tone;
import org.hyperdata.common.describe.DefaultDescriber;
import org.hyperdata.common.describe.Named;

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
		String description = super.describe();
		description += DefaultDescriber.getTypedDescription(this, "beep:PitchFinder");
		return description;
	}
}
