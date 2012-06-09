package at.ac.tuwien.infosys.lsdc.scheduler.matrix;

import java.lang.reflect.Array;
import java.util.Iterator;

public class MatrixIterator implements Iterator<Number[]> {

	private int position = 0;
	private Number[][] myMatrix = null;
	
	public MatrixIterator(Matrix<?> myMatrix) {
		Object[] twoDNumberArray = (Object[])myMatrix.getMatrixObject();
		this.myMatrix = new Number[twoDNumberArray.length][];
		for (int i = 0; i < this.myMatrix.length; i++) {
			this.myMatrix[i] = (Number[])Array.get(myMatrix.getMatrixObject(),i);
		}
	}

	public boolean hasNext() {
		return position < myMatrix.length;
	}

	public Number[] next() {
		return myMatrix[position++];
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
