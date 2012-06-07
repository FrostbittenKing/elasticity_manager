import matrix.Matrix;
import matrix.twoDimensional.MatrixCalcHelper;


public class Test {

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
	
		Matrix<Double> x = new Matrix<Double>(Double.class ,2,2);
		Matrix<Double> y = new Matrix<Double>(Double.class ,2,2);
		x.setMatrix(new Double[]{2.0,3.0},new Double[]{4.0,5.0});
		y.setMatrix(new Double[]{4.0,5.0},new Double[]{6.0,7.0});
		x.divElement(y);
		Matrix<Double>z = MatrixCalcHelper.calculateRowMean(x);
	}

}
