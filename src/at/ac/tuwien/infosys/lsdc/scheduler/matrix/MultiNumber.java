package at.ac.tuwien.infosys.lsdc.scheduler.matrix;

public class MultiNumber {
	
	
	public static Number add(Number x,Number y) {
		if (x.getClass() == Integer.class)
			return x.intValue() + y.intValue();
		else if (x.getClass() == Double.class) {
			return x.doubleValue() + y.doubleValue(); }
		else if (x.getClass() == Float.class)
			return x.floatValue() + y.floatValue();
		else if (x.getClass() == Long.class)
			return x.longValue() + y.longValue();
		else 
			return x.shortValue() + y.shortValue();
	}

	public static Number sub(Number x, Number y) {
		if (x.getClass() == Integer.class)
			return x.intValue() - y.intValue();
		else if (x.getClass() == Double.class)
			return x.doubleValue() - y.doubleValue();
		else if (x.getClass() == Float.class)
			return x.floatValue() - y.floatValue();
		else if (x.getClass() == Long.class)
			return x.longValue() - y.longValue();
		else 
			return x.shortValue() - y.shortValue();
	}

	public static Number mul(Number x, Number y) {
		if (x.getClass() == Integer.class)
			return x.intValue() * y.intValue();
		else if (x.getClass() == Double.class)
			return x.doubleValue() * y.doubleValue();
		else if (x.getClass() == Float.class)
			return x.floatValue() * y.floatValue();
		else if (x.getClass() == Long.class)
			return x.longValue() * y.longValue();
		else 
			return x.shortValue() * y.shortValue();
	}

	public static Number div(Number x, Number y) {	
		if (x.getClass() == Integer.class)
			return x.intValue() / y.intValue();
		else if (x.getClass() == Double.class)
			return x.doubleValue() / y.doubleValue();
		else if (x.getClass() == Float.class)
			return x.floatValue() / y.floatValue();
		else if (x.getClass() == Long.class)
			return x.longValue() / y.longValue();
		else 
			return x.shortValue() / y.shortValue();
	}
	
}
