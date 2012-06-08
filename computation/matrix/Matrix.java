package matrix;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import matrix.twoDimensional.MatrixHelper;

import com.sun.xml.internal.ws.model.FieldSignature;

import sun.reflect.Reflection;

public class Matrix<T> {
	private Object matrixObject = null;
	private int[] dimensions;
	private Class<?> type;


	
	public Class<?> getType() {
		return type;
	}

	public int[] getDimensions() {
		return dimensions;
	}

	public void setDimensions(int[] dimensions) {
		this.dimensions = dimensions;
	}

	public Matrix(Class<?> type, int...dimensions)  {
		//matrix = type.cast(Array.newInstance(type.getComponentType(), dimensions[0]));
		/*for (Integer currentDimension : dimensions)  {
		}*/
		this.dimensions = dimensions;
		
		this.type = type;
		try {
			matrixObject = instantiateArray(Array.newInstance(type, dimensions));
		}  catch (IllegalArgumentException e) {
			throw new MatrixInitializationException(e.getMessage(), e);
		} catch (SecurityException e) {} catch (InstantiationException e) {
			throw new MatrixInitializationException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new MatrixInitializationException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new MatrixInitializationException(e.getMessage(), e);
		} catch (NegativeArraySizeException e) {
			// TODO Auto-generated catch block
			throw new MatrixInitializationException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new MatrixInitializationException(e.getMessage(), e);
		}

	}

	private Object instantiateArray(Object array) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		if (array != null && array.getClass().isArray()) {
			int length = Array.getLength(array);
			for (int i = 0; i < length; i++) {
				Object arrayElement = Array.get(array, i);
				instantiateArray(arrayElement);
				if (array != null && arrayElement == null) {
					Class<?> typeClass = array.getClass().getComponentType();
					Constructor<?> initializer;
					if (typeClass == Integer.class) {
						initializer = array.getClass().getComponentType().getConstructor(Integer.TYPE);
					} 
					else if (typeClass == Double.class) {
						initializer = array.getClass().getComponentType().getConstructor(Double.TYPE);
					}
					else if (typeClass == Float.class) {
						initializer = array.getClass().getComponentType().getConstructor(Float.TYPE);
					}
					else if (typeClass == Long.class) {
						initializer = array.getClass().getComponentType().getConstructor(Long.TYPE);
					}
					else  {
						initializer = array.getClass().getComponentType().getConstructor(Short.TYPE);
					}
					//Array.set(array, i, initializer.newInstance(0));
					Array.set(array,i,null);

				}
			}

		}
		return array;
	}

	public void setMatrix(Object... x) {
		matrixObject = x;
	}
	
	public boolean addRow(Object[] row) {
		if(MatrixHelper.addRow(this, row) == null) {
			return false;
		}
		
		return true;
	}

	public void add(Matrix<T> array) {
		elementOperation(this.matrixObject,array.matrixObject, ElementOp.ADDITION);
	}

	public void sub(Matrix<T> array) {
		elementOperation(this.matrixObject, array.matrixObject, ElementOp.SUBTRACTION);
	}
	
	public void mulElement(Matrix<T> array) {
		elementOperation(this.matrixObject, array.matrixObject, ElementOp.MULTIPLICATION);
	}
	
	public void divElement(Matrix<T> array) {
		elementOperation(this.matrixObject, array.matrixObject, ElementOp.DIVISION);
	}

	public void powElement(Integer exponent) {
		try {
			Matrix<T> copy = (Matrix<T>)this.clone();
			for (Integer i = 0; i < exponent - 1; i++) {
				elementOperation(this.matrixObject, copy.matrixObject, ElementOp.MULTIPLICATION);
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Object elementOperation(Object target, Object source, ElementOp operation) {
		if (target != null) { 
			if (target.getClass().isArray()) {
				int length = Array.getLength(target);
				for (int i = 0; i < length; i++) {
					Object arrayElementTarget = Array.get(target,i);
					Object arrayElementSource = Array.get(source,i);
					Array.set(target, i,elementOperation(arrayElementTarget,arrayElementSource, operation));
				}
			}
			else {
				Class<?> type = target.getClass();
				Number x = (Number)type.cast(target);
				Number y = (Number)type.cast(source);
				switch (operation) {
				case ADDITION:
					return MultiNumber.add(x, y);
				case SUBTRACTION:
					return MultiNumber.sub(x, y);
				case MULTIPLICATION:
					return MultiNumber.mul(x, y);
				case DIVISION:
					return MultiNumber.div(x, y);
				}

			}
		}
		return target;
	}


	public Object getMatrixObject() {
		return matrixObject;
	}

	public void setMatrixObject(Object matrixObject) {
		this.matrixObject = matrixObject;
	}

	private enum ElementOp {
		ADDITION,
		SUBTRACTION,
		MULTIPLICATION,
		DIVISION
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		
			Matrix<?> m =  new Matrix<Number>(type,dimensions);
			m.setMatrix((Object[])cloneMatrix(this.matrixObject));
			return m;
		
	}

	private Object cloneMatrix(Object matrix) {
		Object  clonedMatrix = null;
		if (matrix != null) { 
			if (matrix.getClass().isArray()) {
				int length = Array.getLength(matrix);
				clonedMatrix = new Object[length];
				
				for (int i = 0; i < length; i++) {
					Object arrayElement = Array.get(matrix,i);		
					Array.set(clonedMatrix, i,cloneMatrix(arrayElement));
				}
			}
			else {
				Class<?> type = matrix.getClass();
				Number x = (Number)type.cast(matrix);
			
					if (type == Integer.class) {
						return new Integer(x.intValue());
					} 
					else if (type == Double.class) {
						return new Double(x.doubleValue());
					}
					else if (type == Float.class) {
						return new Float(x.floatValue());
					}
					else if (type == Long.class) {
						return new Long(x.longValue());
					}
					else  {
						return new Short(x.shortValue());
					}


				

			}
		}
		return clonedMatrix;
	}
	
	public MatrixIterator getRowIterator() {
		return new MatrixIterator(this);
	}
	
}

