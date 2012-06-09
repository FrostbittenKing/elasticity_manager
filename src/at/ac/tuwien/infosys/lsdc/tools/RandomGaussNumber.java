package at.ac.tuwien.infosys.lsdc.tools;
import java.util.*;

public class RandomGaussNumber {


	public static int STANDARD_NORMAL_DISTRIBUTION_MAX = 4;

	private static Random randomGen = new Random();

	public static double [] generateGaussianSeries(int min, int max, int length) {

		double [] series = new double[length];
		for (int i = 0; i < length; i++) {
			series[i] = nextGaussian(min,max);
		}
		return series;
	}

	public static double newGaussianDouble(int min, int max) {
		return nextGaussian(min, max);
	}

	public static int newGaussianInt(int min, int max) {
		return Math.round((float)nextGaussian(min, max));
	}

	private static double nextGaussian(int min, int max){
		int mean = (min + max) / 2;
		double nextValue;
		do {
			nextValue = randomGen.nextGaussian() * mean / STANDARD_NORMAL_DISTRIBUTION_MAX + mean;
		} while (nextValue < min || nextValue > max);
		return nextValue;
	}
}