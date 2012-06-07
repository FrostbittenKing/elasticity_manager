package matrix.twoDimensional;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import matrix.Matrix;
import matrix.MatrixIllegalOperationException;
import matrix.MatrixIterator;
import matrix.MultiNumber;

public class MatrixCalcHelper {
	public static Matrix<Double> calculateRowMean(Matrix<?> matrix) {
		int[] dim = matrix.getDimensions();
		int pos = 0;
		Number[] rowMean = new Number[dim[0]];
		if (isTwoDimensional(matrix)) {
			getRowMean(matrix, pos, rowMean);
			Matrix<Double> returnVector = new Matrix<Double>(Double.class, 1,rowMean.length);
			returnVector.setMatrix((Object[])rowMean);
			return returnVector;
		}
		return null;
	}
	
	public static Matrix<Float> calculateRowMeanFloat(Matrix<?> matrix) {
		int[] dim = matrix.getDimensions();
		int pos = 0;
		Number[] rowMean = new Number[dim[0]];
		if (isTwoDimensional(matrix)) {
			getRowMean(matrix, pos, rowMean);
			Matrix<Float> returnVector = new Matrix<Float>(Float.class, 1,rowMean.length);
			returnVector.setMatrix((Object[])rowMean);
			return returnVector;
		}
		return null;
	}

	private static void getRowMean(Matrix<?> matrix, int pos, Number[] rowMean) {
		MatrixIterator iterator = matrix.getRowIterator();
		try {
			Field field = matrix.getType().getField("TYPE");


			while (iterator.hasNext()) {
				Number[] currentNumberRow = iterator.next();
				Constructor<?> initializerConstructor = matrix.getType().getConstructor((Class<?>)field.get(currentNumberRow[0]));
				rowMean[pos] = (Number)initializerConstructor.newInstance((Object)0);
				for (Number currentNumber : currentNumberRow) {
					rowMean[pos] = MultiNumber.add(rowMean[pos], currentNumber);
				}
				rowMean[pos] = MultiNumber.div(rowMean[pos], currentNumberRow.length);
				pos++;
			}
		} catch (SecurityException e) {
			throw new MatrixIllegalOperationException(e.getMessage(), e.getCause());
		} catch (NoSuchFieldException e) {
			throw new MatrixIllegalOperationException(e.getMessage(), e.getCause());
		} catch (IllegalArgumentException e) {
			throw new MatrixIllegalOperationException(e.getMessage(), e.getCause());
		} catch (IllegalAccessException e) {
			throw new MatrixIllegalOperationException(e.getMessage(), e.getCause());
		} catch (NoSuchMethodException e) {
			throw new MatrixIllegalOperationException(e.getMessage(), e.getCause());
		} catch (InstantiationException e) {
			throw new MatrixIllegalOperationException(e.getMessage(), e.getCause());
		} catch (InvocationTargetException e) {
			throw new MatrixIllegalOperationException(e.getMessage(), e.getCause());
		} 
	}

	private static boolean isTwoDimensional(Matrix<?> matrix) {
		return (getDepth(matrix.getMatrixObject()) == 2);
	}

	private static Integer getDepth(Object matrix) {
		int depth = 0;
		Class<?> matrixClass = matrix.getClass();
		if (matrixClass.isArray()) {
			depth += getDepth(Array.get(matrix, 0));
			return ++depth;
		}
		else {
			return 0;
		}
	}
}
