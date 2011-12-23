/**
 * 
 */
package org.hyperdata.go;

import java.util.*;

import org.hyperdata.beeps.ParameterizedCodecGoertzel;
import org.hyperdata.beeps.util.Plotter;
import org.hyperdata.go.parameters.DefaultParameterList;
import org.hyperdata.go.parameters.FitnessComparator;
import org.hyperdata.go.parameters.ParameterList;

/**
 * @author danny
 * 
 */
public class Population {

	static int populationSize = 16; // must be multiple of 8
	static int generations = 10;
	static int minCharacters = 5; 
	static int maxCharacters = 23; 
	
	private List<Organism> organisms = new ArrayList<Organism>();

	public void add(Organism system) {
		organisms.add(system);
	}

	public Organism get(int i) {
		return organisms.get(i);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Population population = new Population();
		System.out.println("Initializing :");
		for (int i = 0; i < populationSize; i++) {
			System.out.print(i + " ");
			ParameterizedCodecGoertzel codec = new ParameterizedCodecGoertzel();
			codec.setnCharacters(minCharacters, maxCharacters);

			codec.init();
			population.add(codec);
		}
		System.out.println();
		for (int generation = 0; generation < generations; generation++) {
			System.out.println("******   Generation : " + generation
					+ "   ******");
			System.out.println("Testing organism :");
			for (int i = 0; i < populationSize; i++) {
				System.out.print(i + " ");
				population.get(i).run();
			}
			population.sort();

			Organism fittest = population.get(0);
			
			System.out.println("------vvvv----------------");
			System.out.println("Top fitness = "+fittest.getFitness());
			System.out.println("age = "+fittest.getAge());
			System.out.println("accuracy = "
					+ Plotter.roundToSignificantFigures(
							((ParameterizedCodecGoertzel) fittest).getAccuracy(), 2));
			System.out.println("time = "
					+ Plotter.roundToSignificantFigures(
							((ParameterizedCodecGoertzel) fittest).getRunTime(), 2));
			System.out.println(fittest.getParameters());
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
		System.out.println("time = "
				+ Plotter.roundToSignificantFigures(
						((ParameterizedCodecGoertzel) fittest).getRunTime(), 2));
		System.out.println("accuracy = "
				+ Plotter.roundToSignificantFigures(
						((ParameterizedCodecGoertzel) fittest).getAccuracy(), 2));
		System.out.println(fittest.getParameters());
	}

	/**
	 * @return
	 */
	public int size() {

		return organisms.size();
	}

	// int mutateEvery = 8;
	
	/*
	 * [Sum] Calculate sum of all chromosome fitnesses in population - sum S.
	 * [Select] Generate random number from interval (0,S) - r. [Loop] Go
	 * through the population and sum fitnesses from 0 - sum s. When the sum s
	 * is greater then r, stop and return the chromosome where you are.
	 */
	public void breed() {
		List<Organism> nextGeneration = new ArrayList<Organism>();

		// copy across the best 1/4
		for (int i = 0; i < populationSize/4; i++) {
			Organism organism = organisms.get(i);
			organism.setAge(organism.getAge()+1);
			nextGeneration.add(organism);
		}
		
		// middle 1/2 are products of breeding
		for (int i = 0; i < populationSize/2; i++) {

			// favour more successful for selection 
			double cubeRoot = Math.exp(Math.log((double)i)/3);
			double scale = 1/(1 + cubeRoot);
			
			int p1 = (int)(Math.random() * scale *populationSize);
			int p2 = (int)(Math.random() * scale * populationSize);
			while(p2 == p1) { // there's a good chance of it
				p2 = (int)(Math.random() * scale * populationSize);
			}

			Organism parent1 = organisms.get(p1);
			Organism parent2 = organisms.get(p2);
//			System.out.println("p1 : "+p1+ " f="+parent1.getFitness());
//			System.out.println("p2 : "+p2+ " f="+parent2.getFitness());
			Organism child = crossover(parent1, parent2);
			nextGeneration.add(child);
		}
		// System.out.println("B "+nextGeneration.size());
		
		// copy and mutate top 1/8
		for (int i = 0; i < populationSize/8; i++) {
			// Organism target = organisms.get((int)(populationSize * Math.random()));
			Organism mutant = mutate(organisms.get(i));
			nextGeneration.add(mutant);
		}
		// last 1/8, totally random
		for (int i = 0; i < populationSize/8; i++) {
			ParameterizedCodecGoertzel codec = new ParameterizedCodecGoertzel();
			codec.init();
			nextGeneration.add(codec);
		}
		organisms = nextGeneration;
	}

	/**
	 * @param target
	 */
	private Organism mutate(Organism target) {
		ParameterizedCodecGoertzel mutant = new ParameterizedCodecGoertzel();
		mutant.init();
		ParameterList pl = new DefaultParameterList(mutant.getParameters());
		int r = (int)((double)pl.size() * Math.random());
		pl.get(r).initRandom();
		return mutant;
	}

	/**
	 * @param parent1
	 * @param parent2
	 * @return
	 */
	private Organism crossover(Organism parent1, Organism parent2) {
		ParameterList pl1 = parent1.getParameters();
		ParameterList pl2 = parent2.getParameters();
		// System.out.println("pl1.size()="+pl1.size());
		int crossoverPoint = (int) ((double)pl1.size() * Math.random());
// System.out.println("crossoverPoint = "+crossoverPoint);
		ParameterList childParameters = new DefaultParameterList();

		for (int j = 0; j < crossoverPoint; j++) {
			childParameters.add(pl1.get(j));
		}
		for (int j = crossoverPoint; j < pl2.size(); j++) {
			childParameters.add(pl2.get(j));
		}
//		System.out.println("**** PARENT 1 ****");
//		System.out.println(pl1);
//		System.out.println("**** PARENT 2 ****");
//		System.out.println(pl2);
//		System.out.println("**** CHILD ****");
		ParameterizedCodecGoertzel child = new ParameterizedCodecGoertzel();
		child.init();
		child.setParameters(childParameters);
		
//		System.out.println(child.getParameters());
//		System.out.println("**** END CHILD ****");
		return child;
	}

	/**
	 * highest value fitness first
	 */
	public void sort() {
		Collections.sort(organisms, new FitnessComparator());
	}
}
