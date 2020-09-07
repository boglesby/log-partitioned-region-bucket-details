package example.server.function;

import example.PartitionedRegionDetails;
import org.apache.geode.cache.Declarable;

import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;

import org.apache.geode.internal.cache.PartitionedRegion;

public class LogPartitionedRegionBucketDetailsFunction implements Function, Declarable {

  public void execute(FunctionContext context) {
    RegionFunctionContext rfc = (RegionFunctionContext) context;
    PartitionedRegion pr = (PartitionedRegion) rfc.getDataSet();

    // Create the PartitionedRegion details
    PartitionedRegionDetails prDetails = new PartitionedRegionDetails(pr);

    // Log the PartitionedRegion details
    context.getCache().getLogger().info(prDetails.toString());

    // Return the result
    context.getResultSender().lastResult(true);
  }

  public String getId() {
    return getClass().getSimpleName();
  }

  public boolean optimizeForWrite() {
    return true;
  }
}
