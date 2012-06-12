package at.ac.tuwien.infosys.lsdc.scheduler.statistics;

public class GnuPlotOutputDataConverter {
	public static String[][] doubleInput(Double[][] data) {
		String[][] output = new String[data.length][data[0].length];
		int y = 0;
		int x = 0;
		for (Double[] row : data) {
			for (Double value: row) {
				output[y][x++] = String.valueOf(value);
			}
			y++;
		}
		return output;
	}
}
