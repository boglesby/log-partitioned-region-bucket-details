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

  private long entries;

  private long primaryEntries;

  private long redundantEntries;

  private long entriesInMemory;

  private long primaryEntriesInMemory;

  private long redundantEntriesInMemory;

  private long entriesOnDisk;

  private long primaryEntriesOnDisk;

  private long redundantEntriesOnDisk;

  private long bytes;

  private long primaryBytes;

  private long redundantBytes;

  private long bytesInMemory;

  private long primaryBytesInMemory;

  private long redundantBytesInMemory;

  private long bytesOnDisk;

  private long primaryBytesOnDisk;

  private long redundantBytesOnDisk;

  private List<BucketRegionDetails> primaryBucketRegionDetails;

  private List<BucketRegionDetails> redundantBucketRegionDetails;

  private static final Format format = NumberFormat.getInstance();

  public PartitionedRegionDetails() {}

  public PartitionedRegionDetails(PartitionedRegion pr) {
    this.regionPath = pr.getFullPath();
    this.primaryBucketRegionDetails = new ArrayList<>();
    this.redundantBucketRegionDetails = new ArrayList<>();
    initialize(pr);
  }

  private void initialize(PartitionedRegion pr) {
    pr.getDataStore().getAllLocalBucketRegions()
      .stream()
      .sorted(Comparator.comparingInt(BucketRegion::getId))
      .forEach(
        br -> {
          BucketRegionDetails bri = new BucketRegionDetails(pr, br);
          initializeTotalDetails(bri);
          if (pr.getRegionAdvisor().isPrimaryForBucket(br.getId())) {
            initializeTotalPrimaryDetails(bri);
          } else {
            initializeTotalRedundantDetails(bri);
          }
        });
  }

  private void initializeTotalDetails(BucketRegionDetails bri) {
    this.entries += bri.getEntries();
    this.entriesInMemory += bri.getEntriesInMemory();
    this.entriesOnDisk += bri.getEntriesOnDisk();
    this.bytes += bri.getBytes();
    this.bytesInMemory += bri.getBytesInMemory();
    this.bytesOnDisk += bri.getBytesOnDisk();
  }

  private void initializeTotalPrimaryDetails(BucketRegionDetails bri) {
    this.primaryBucketRegionDetails.add(bri);
    this.primaryEntries += bri.getEntries();
    this.primaryEntriesInMemory += bri.getEntriesInMemory();
    this.primaryEntriesOnDisk += bri.getEntriesOnDisk();
    this.primaryBytes += bri.getBytes();
    this.primaryBytesInMemory += bri.getBytesInMemory();
    this.primaryBytesOnDisk += bri.getBytesOnDisk();
  }

  private void initializeTotalRedundantDetails(BucketRegionDetails bri) {
    this.redundantBucketRegionDetails.add(bri);
    this.redundantEntries += bri.getEntries();
    this.redundantEntriesInMemory += bri.getEntriesInMemory();
    this.redundantEntriesOnDisk += bri.getEntriesOnDisk();
    this.redundantBytes += bri.getBytes();
    this.redundantBytesInMemory += bri.getBytesInMemory();
    this.redundantBytesOnDisk += bri.getBytesOnDisk();
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
      .append("Region ")
      .append(this.regionPath)
      .append(" Bucket Details:")
      .append("\n");
    addTotalDetails(builder);
    addTotalPrimaryDetails(builder);
    addTotalRedundantDetails(builder);
    addBucketDetails(builder, this.primaryBucketRegionDetails, "Primary");
    addBucketDetails(builder, this.redundantBucketRegionDetails, "Redundant");
    return builder.toString();
  }

  private void addTotalDetails(StringBuilder builder) {
    builder
      .append("\n\tTotal ")
      .append("buckets=")
      .append(format.format(this.primaryBucketRegionDetails.size() + this.redundantBucketRegionDetails.size()))
      .append("; entries=")
      .append(format.format(entries))
      .append("; entriesInMemory=")
      .append(format.format(entriesInMemory))
      .append("; entriesOnDisk=")
      .append(format.format(entriesOnDisk))
      .append("; bytes=")
      .append(format.format(bytes))
      .append("; bytesInMemory=")
      .append(format.format(bytesInMemory))
      .append("; bytesOnDisk=")
      .append(format.format(bytesOnDisk));
  }

  private void addTotalPrimaryDetails(StringBuilder builder) {
    builder
      .append("\n\tPrimary ")
      .append("buckets=")
      .append(format.format(this.primaryBucketRegionDetails.size()))
      .append("; entries=")
      .append(format.format(primaryEntries))
      .append("; entriesInMemory=")
      .append(format.format(primaryEntriesInMemory))
      .append("; entriesOnDisk=")
      .append(format.format(primaryEntriesOnDisk))
      .append("; bytes=")
      .append(format.format(primaryBytes))
      .append("; bytesInMemory=")
      .append(format.format(primaryBytesInMemory))
      .append("; bytesOnDisk=")
      .append(format.format(primaryBytesOnDisk));
  }

  private void addTotalRedundantDetails(StringBuilder builder) {
    builder
      .append("\n\tRedundant ")
      .append("buckets=")
      .append(format.format(this.redundantBucketRegionDetails.size()))
      .append("; entries=")
      .append(format.format(redundantEntries))
      .append("; entriesInMemory=")
      .append(format.format(redundantEntriesInMemory))
      .append("; entriesOnDisk=")
      .append(format.format(redundantEntriesOnDisk))
      .append("; bytes=")
      .append(format.format(redundantBytes))
      .append("; bytesInMemory=")
      .append(format.format(redundantBytesInMemory))
      .append("; bytesOnDisk=")
      .append(format.format(redundantBytesOnDisk));
  }

  private void addBucketDetails(StringBuilder builder, List<BucketRegionDetails> bucketRegionDetails, String type) {
    builder
      .append("\n\n")
      .append(type)
      .append(" Buckets:")
      .append("\n");
    for (BucketRegionDetails brDetails : bucketRegionDetails) {
      builder.append("\n\t").append(brDetails);
    }
  }
}
