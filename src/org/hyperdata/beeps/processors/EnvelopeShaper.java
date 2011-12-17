/**
 * 
 */
package org.hyperdata.beeps.processors;

import java.util.List;

import org.hyperdata.beeps.Debug;
import org.hyperdata.beeps.pipelines.DefaultParameterized;
import org.hyperdata.beeps.pipelines.DefaultProcessor;
import org.hyperdata.beeps.util.Tone;

/**
 * @author danny
 * 
 */
public class EnvelopeShaper extends DefaultProcessor {
	


	private double attackProportion = 0;
	private double decayProportion = 0;

	public EnvelopeShaper(){
		super("EnvelopeShaper");
	}
	
	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#process(org.hyperdata.beeps.util.Tone)
	 */
	@Override
	public Tone process(Tone input) {
		return process(input);
	}
	
//	public List<Double> process(List<Double> samples){
//		return applyEnvelope(samples, (Double)getParameter("attackProportion"), (Double)getParameter("decayProportion"));
//	}
	
	public List<Double> process(List<Double> samples) {
		// System.out.println("SAMPLES="+samples.size());
		double attackMarker = ((double) samples.size()) * attackProportion;
		// System.out.println("Attack marker="+attackMarker);
		double decayMarker = ((double) samples.size())
				* (1.0 - decayProportion);
		// System.out.println("Decay marker="+decayMarker);
		for (int i = 0; i < attackMarker; i++) {

			double scale = ((double) i) / attackMarker;
			// double value = samples.get(i) - WaveMaker.MAX_VALUE/2;
			// System.out.println("Attack Scaling by "+scale);
			samples.set(i, scale * samples.get(i));
		}

		for (int i = samples.size()-1; i > decayMarker; i--) {
			double scale = 1.0-((double)(i-decayMarker)) / (double)(samples.size()-decayMarker);
			// System.out.println("Decay Scaling by "+scale);
			samples.set(i, scale * samples.get(i));
		}

		return samples;
	}
	
	/**
	 * @param decayProportion the decayProportion to set
	 */
	public void setDecayProportion(double decayProportion) {
		this.decayProportion = decayProportion;
	}

	/**
	 * @param attackProportion the attackProportion to set
	 */
	public void setAttackProportion(double attackProportion) {
		this.attackProportion = attackProportion;
	}

	/* (non-Javadoc)
	 * @see org.hyperdata.beeps.pipelines.Processor#initFromParameters()
	 */
	@Override
	public void initFromParameters() {
		Debug.debug("parameters="+parameters);
//		Debug.halt(this);
// System.out.println("parameters.get(attackProportion)="+parameters.get("attackProportion"));
		setAttackProportion((Double)parameters.get("attackProportion"));
		setDecayProportion((Double)parameters.get("decayProportion"));
		
	}
}
