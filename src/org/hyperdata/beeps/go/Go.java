/**
 * 
 */
package org.hyperdata.beeps.go;

import org.hyperdata.beeps.GoCodec;
import org.hyperdata.beeps.parameters.ParameterList;
import org.hyperdata.beeps.parameters.ParameterListFile;
import org.hyperdata.beeps.parameters.TimeStampParameter;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.common.Describer;

/**
 * @author danny
 * 
 */
public class Go {

	static int populationSize = 64; // must be multiple of 8
	static int generations = 100;
	static int minCharacters = 4;
	static int maxCharacters = 25;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		Population population = new Population();
		// System.out.println(Describer.extractClassURI(population));
		System.out.println("Initializing :");
		for (int i = 0; i < Go.populationSize; i++) {
			System.out.print(i + " ");
			GoCodec codec = new GoCodec();
			codec.setnCharacters(Go.minCharacters,
					Go.maxCharacters);

			codec.init();
			codec.initRandom();
		//	System.out.println("initrandom SIZE = "+codec.parametersSize());
			//codec.initFromParameters();
		//	System.out.println(codec);
			population.add(codec);
		}
		
		////////////// seed 
		 String configFilename = "/home/danny/workspace/WebBeep/data/config.xml";
			ParameterListFile plf = new ParameterListFile();
			ParameterList config = plf.load(configFilename);
			System.out.println("==============PLF = \n"+plf);
			for(int i=0;i<population.size()/8;i++){
				int r =(int)( Math.random()*(double)population.size());
				ParameterList targetParameters = population.get(r).getParameters();
				targetParameters.consume(config);
				population.get(r).setParameters(targetParameters); // probably not necessary, by-reference etc.
			//	population.get(r).initFromParameters();
			//	System.out.println("seed SIZE = "+   (((GoCodec) (population.get(r)    )).parametersSize()));
			}
			
		System.out.println();
		for (int generation = 0; generation < Go.generations; generation++) {
			System.out.println("******   Generation : " + generation
					+ "   ******");
			System.out.println("Testing organism :");
			for (int i = 0; i < Go.populationSize; i++) {
				System.out.print(i + " ");
			//	System.out.println("SIZE = "+((GoCodec) population.get(i)).parametersSize());
				population.get(i).run();
			}
			population.sort();

			Organism fittest = population.get(0);

			System.out.println("------vvvv----------------");
			System.out.println("Top instance fitness = " + fittest.getFitness());
			System.out.println("Top mean fitness = " + fittest.getMeanFitness());
			System.out.println("age = " + fittest.getAge());
			System.out.println("accuracy = "
					+ Plotter.roundToSignificantFigures(
							((DefaultOrganism) fittest)
									.getAccuracy(), 2));
			System.out
					.println("time = "
							+ Plotter.roundToSignificantFigures(
									((DefaultOrganism) fittest)
											.getRunTime(), 2));
		//	System.out.println(fittest.getParameters());
			
			plf = new ParameterListFile();
			plf.save(fittest.getParameters(),
					"/home/danny/workspace/WebBeep/data/fittest.xml");
			
		/////////////////////////	System.out.println(fittest.getParameters());
	//		System.out.println("SIZE = "+fittest.getParameters().size());
			System.out.println("------^^^^^----------------");

			if (generation % 10 == 0) {
				// System.out.println(fittest.getParameters());
			}
			population.breed();
		}
		Organism fittest = population.get(0);
		System.out.println("*******************************");

		System.out.println("Total time = "
				+ ((double) System.currentTimeMillis() - (double) startTime)
				/ 1000);
		System.out.println("Fittess = " + fittest.getFitness());
		System.out
				.println("time = "
						+ Plotter.roundToSignificantFigures(
								((DefaultOrganism) fittest)
										.getRunTime(), 2));
		System.out
				.println("accuracy = "
						+ Plotter.roundToSignificantFigures(
								((DefaultOrganism) fittest)
										.getAccuracy(), 2));
		fittest.getParameters().add(new TimeStampParameter());
 
	//	System.out.println(fittest.getParameters());
	//	System.out.println(fittest);	

	}

}
