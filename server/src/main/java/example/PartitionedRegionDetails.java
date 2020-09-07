package example;

import org.apache.geode.internal.cache.BucketRegion;
import org.apache.geode.internal.cache.PartitionedRegion;

import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PartitionedRegionDetails {

  private String regionPath;

  private List<BucketRegionDetails> primaryBucketRegionDetails;

  private List<BucketRegionDetails> redundantBucketRegionDetails;

  private static final Format format = NumberFormat.getInstance();

  public PartitionedRegionDetails() {}

  public PartitionedRegionDetails(PartitionedRegion pr) {
    this.regionPath = pr.getFullPath();
    initialize(pr);
  }

  private void initialize(PartitionedRegion pr) {
    this.primaryBucketRegionDetails = new ArrayList<>();
    this.redundantBucketRegionDetails = new ArrayList<>();
    pr.getDataStore().getAllLocalBucketRegions()
      .stream()
      .sorted(Comparator.comparingInt(BucketRegion::getId))
      .forEach(
        br -> {
          BucketRegionDetails bri = new BucketRegionDetails(pr, br);
          if (pr.getRegionAdvisor().isPrimaryForBucket(br.getId())) {
            this.primaryBucketRegionDetails.add(bri);
          } else {
            this.redundantBucketRegionDetails.add(bri);
          }
        });
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\n");
    addBucketRegionDetails(builder, this.primaryBucketRegionDetails, "primary");
    builder.append("\n\n");
    addBucketRegionDetails(builder, this.redundantBucketRegionDetails, "redundant");
    return builder.toString();
  }

  private void addBucketRegionDetails(StringBuilder builder, List<BucketRegionDetails> bucketRegionDetails, String type) {
    long totalEntries=0l, totalEntriesInMemory=0l, totalEntriesOnDisk=0l,
      totalBytes=0l, totalBytesInMemory=0l, totalBytesOnDisk=0l;
    builder
      .append("Region ")
      .append(this.regionPath)
      .append(" contains the following ")
      .append(bucketRegionDetails.size())
      .append(" ")
      .append(type)
      .append(" buckets:");
    for (BucketRegionDetails brDetails : bucketRegionDetails) {
      totalEntries += brDetails.getEntries();
      totalEntriesInMemory += brDetails.getEntriesInMemory();
      totalEntriesOnDisk += brDetails.getEntriesOnDisk();
      totalBytes += brDetails.getBytes();
      totalBytesInMemory += brDetails.getBytesInMemory();
      totalBytesOnDisk += brDetails.getBytesOnDisk();
      builder.append("\n\t").append(brDetails);
    }
    builder
      .append("\n\tTotal ")
      .append("entries=")
      .append(format.format(totalEntries))
      .append("; entriesInMemory=")
      .append(format.format(totalEntriesInMemory))
      .append("; entriesOnDisk=")
      .append(format.format(totalEntriesOnDisk))
      .append("; bytes=")
      .append(format.format(totalBytes))
      .append("; bytesInMemory=")
      .append(format.format(totalBytesInMemory))
      .append("; bytesOnDisk=")
      .append(format.format(totalBytesOnDisk));
  }
}
