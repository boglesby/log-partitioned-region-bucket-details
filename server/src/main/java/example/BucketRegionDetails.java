package example;

import lombok.Getter;
import org.apache.geode.internal.cache.BucketRegion;
import org.apache.geode.internal.cache.PartitionedRegion;

import java.text.Format;
import java.text.NumberFormat;

@Getter
public class BucketRegionDetails {

  private int bucketId;

  private long entries;

  private long entriesInMemory;

  private long entriesOnDisk;

  private long bytes;

  private long bytesInMemory;

  private long bytesOnDisk;

  private static final Format format = NumberFormat.getInstance();

  public BucketRegionDetails() {}

  public BucketRegionDetails(PartitionedRegion pr, BucketRegion br) {
    initialize(pr, br);
  }

  private void initialize(PartitionedRegion pr, BucketRegion br) {
    this.bucketId = br.getId();
    this.entries = br.size();
    this.entriesInMemory = br.getNumEntriesInVM();
    this.entriesOnDisk = br.getNumOverflowOnDisk();
    this.bytes = br.getTotalBytes();
    this.bytesInMemory = br.getBytesInMemory();
    this.bytesOnDisk = br.getNumOverflowBytesOnDisk();
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
      .append("Bucket ")
      .append("id=")
      .append(this.bucketId)
      .append("; entries=")
      .append(format.format(this.entries))
      .append("; entriesInMemory=")
      .append(format.format(this.entriesInMemory))
      .append("; entriesOnDisk=")
      .append(format.format(this.entriesOnDisk))
      .append("; bytes=")
      .append(format.format(this.bytes))
      .append("; bytesInMemory=")
      .append(format.format(this.bytesInMemory))
      .append("; bytesOnDisk=")
      .append(format.format(this.bytesOnDisk));
    return builder.toString();
  }
}
