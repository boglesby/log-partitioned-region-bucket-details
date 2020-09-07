package example.client.function;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

@OnRegion(region = "Trade")
public interface OnRegionFunctions {

  @FunctionId("LogPartitionedRegionBucketDetailsFunction")
  Object logPartitionedRegionBucketDetails();
}