package at.ac.tuwien.infosys.lsdc.scheduler.heuristics;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;
import at.ac.tuwien.infosys.lsdc.scheduler.matrix.Matrix;

public class PMLoadMatrix extends Matrix<Number>{

	public PMLoadMatrix(Class<?> type, int[] dimensions) {
		super(type, dimensions);
	}
	
	public PMLoadMatrix(int dimX, int dimY) {
		super(Double.class, new int[] {dimY, dimX});
	}
	
	public void addResource(Resource resource) {
		super.addRow(resource.getResources());
	}

}
