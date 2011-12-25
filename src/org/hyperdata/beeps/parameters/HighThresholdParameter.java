/**
 * 
 */
package org.hyperdata.beeps.parameters;


/**
 * @author danny
 *
 */
public class HighThresholdParameter extends DefaultParameter {

	public HighThresholdParameter(){
		
	}
	
	/**
	 * @param processor
	 * @param parameterName
	 */
	public HighThresholdParameter(Parameterized processor, String parameterName) {
		super(processor, parameterName);
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.go.parameters.Parameter#initRandom()
	 */
	@Override
	public void initRandom() {
		// TODO Auto-generated method stub
		Parameterized p = getProcessor();
		Object low = p.getParameter("lowThreshold");
		
		
		if(low != null) {
			value = (Double)low + Math.random() * (1-(Double)low);
		}else { // give it a sane fallback, just in case
			value = 0.5 + Math.random()/2;
		}
	}

}
