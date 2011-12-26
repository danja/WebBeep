/**
 * 
 */
package org.hyperdata.beeps.go;

import org.hyperdata.beeps.ParameterizedCodecGoertzel;
import org.hyperdata.beeps.parameters.ParameterListFile;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.common.Describer;

/**
 * @author danny
 * 
 */
public class Go {

	static int populationSize = 128; // must be multiple of 8
	static int generations = 100;
	static int minCharacters = 5;
	static int maxCharacters = 40;
	
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
			ParameterizedCodecGoertzel codec = new ParameterizedCodecGoertzel();
			codec.setnCharacters(Go.minCharacters,
					Go.maxCharacters);

			codec.init();
			System.out.println(codec);
			population.add(codec);
		}
		System.out.println();
		for (int generation = 0; generation < Go.generations; generation++) {
			System.out.println("******   Generation : " + generation
					+ "   ******");
			System.out.println("Testing organism :");
			for (int i = 0; i < Go.populationSize; i++) {
				System.out.print(i + " ");
				population.get(i).run();
			}
			population.sort();

			Organism fittest = population.get(0);

			System.out.println("------vvvv----------------");
			System.out.println("Top fitness = " + fittest.getFitness());
			System.out.println("age = " + fittest.getAge());
			System.out.println("accuracy = "
					+ Plotter.roundToSignificantFigures(
							((ParameterizedCodecGoertzel) fittest)
									.getAccuracy(), 2));
			System.out
					.println("time = "
							+ Plotter.roundToSignificantFigures(
									((ParameterizedCodecGoertzel) fittest)
											.getRunTime(), 2));
		//	System.out.println(fittest.getParameters());
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
								((ParameterizedCodecGoertzel) fittest)
										.getRunTime(), 2));
		System.out
				.println("accuracy = "
						+ Plotter.roundToSignificantFigures(
								((ParameterizedCodecGoertzel) fittest)
										.getAccuracy(), 2));
		System.out.println(fittest.getParameters());

		ParameterListFile plf = new ParameterListFile();
		plf.save(fittest.getParameters(),
				"/home/danny/workspace/WebBeep/data/fittest.xml");
	}

}
