package at.ac.tuwien.infosys.lsdc.cloud.cluster;

import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.exceptions.CloudClusterCreationException;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public interface ICloudClusterFactory {
 public ICloudClusterManager createLocalCluster(HashMap<Integer, PhysicalMachine> machines);
}
