# Log PartitionedRegion Bucket Details
## Description

This project provides a function that logs PartitionedRegion bucket entry and size details.

The **LogPartitionedRegionBucketDetailsFunction**:

- gets the PartitionedRegion
- creates and logs a PartitionedRegionDetails

The **PartitionedRegionDetails** iterates the PartitionedRegion buckets and for each:

- creates a BucketRegionDetails
- adds the BucketRegionDetails to the appropriate list (primary or redundant)

Each **BucketRegionDetails** encapsulates:

- the number of bucket entries
- the number of bucket entries in memory
- the number of bucket entries on disk
- the number of bucket bytes
- the number of bucket bytes in memory
- the number of bucket bytes on disk

## Initialization
Modify the **GEODE** environment variable in the *setenv.sh* script to point to a Geode installation directory.
## Build
Build the Spring Boot Client Application and Geode Server Function and supporting classes using gradle like:

```
./gradlew clean jar bootJar
```
## Run Example
### Start and Configure Locator and Servers
Start and configure the locator and 3 servers using the *startandconfigure.sh* script like:

```
./startandconfigure.sh
```
### Load Entries
Run the client to load N Trade instances into the Trade Region using the *runclient.sh* script like below.

The parameters are:

- operation (load-all-region)
- number of entries (500000)

```
./runclient.sh load-all-region 500000
```
### Log PartitionedRegion Bucket Details
Execute the **LogPartitionedRegionBucketDetailsFunction** to log the PartitionedRegion bucket details using the *runclient.sh* script like below.

The parameters are:

- operation (log-partitioned-region-bucket-details)

```
./runclient.sh log-partitioned-region-bucket-details
```
### Shutdown Locator and Servers
Execute the *shutdownall.sh* script to shutdown the servers and locators like:

```
./shutdownall.sh
```
### Remove Locator and Server Files
Execute the *cleanupfiles.sh* script to remove the server and locator files like:

```
./cleanupfiles.sh
```
## Sample Output
### Start and Configure Locator and Servers
Sample output from the *startandconfigure.sh* script is:

```
./startandconfigure.sh 
1. Executing - start locator --name=locator

...........
Locator in <working-directory>/locator on xxx.xxx.x.xx[10334] as locator is currently online.
Process ID: 49199
Uptime: 12 seconds
Geode Version: 1.12.0
Java Version: 1.8.0_151
Log File: <working-directory>/locator/locator.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

Successfully connected to: JMX Manager [host=xxx.xxx.x.xx, port=1099]

Cluster configuration service is up and running.

2. Executing - set variable --name=APP_RESULT_VIEWER --value=any

Value for variable APP_RESULT_VIEWER is now: any.

3. Executing - configure pdx --read-serialized=true --auto-serializable-classes=.*

read-serialized = true
ignore-unread-fields = false
persistent = false
PDX Serializer = org.apache.geode.pdx.ReflectionBasedAutoSerializer
Non Portable Classes = [.*]
Cluster configuration for group 'cluster' is updated.

4. Executing - start server --name=server-1 --server-port=0 --eviction-heap-percentage=75 --initial-heap=512m --max-heap=512m --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false

.........
Server in <working-directory>/server-1 on xxx.xxx.x.xx[57716] as server-1 is currently online.
Process ID: 49209
Uptime: 7 seconds
Geode Version: 1.12.0
Java Version: 1.8.0_151
Log File: <working-directory>/server-1/cacheserver.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

5. Executing - start server --name=server-2 --server-port=0 --eviction-heap-percentage=75 --initial-heap=512m --max-heap=512m --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false

........
Server in <working-directory>/server-2 on xxx.xxx.x.xx[57738] as server-2 is currently online.
Process ID: 49211
Uptime: 1 minute 4 seconds
Geode Version: 1.12.0
Java Version: 1.8.0_151
Log File: <working-directory>/server-2/cacheserver.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

6. Executing - start server --name=server-3 --server-port=0 --eviction-heap-percentage=75 --initial-heap=512m --max-heap=512m --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false

.........
Server in <working-directory>/server-3 on xxx.xxx.x.xx[57777] as server-3 is currently online.
Process ID: 49227
Uptime: 7 seconds
Geode Version: 1.12.0
Java Version: 1.8.0_151
Log File: <working-directory>/server-3/cacheserver.log
JVM Arguments: -<jvm-arguments>
Class-Path: <classpath>

7. Executing - list members

Member Count : 4

  Name   | Id
-------- | ---------------------------------------------------------------
locator  | xxx.xxx.x.xx(locator:49199:locator)<ec><v0>:41000 [Coordinator]
server-1 | xxx.xxx.x.xx(server-1:49209)<v1>:41001
server-2 | xxx.xxx.x.xx(server-2:49211)<v2>:41002
server-3 | xxx.xxx.x.xx(server-3:49227)<v3>:41003

8. Executing - create region --name=Trade --type=PARTITION_REDUNDANT_HEAP_LRU --eviction-action=overflow-to-disk

 Member  | Status | Message
-------- | ------ | -------------------------------------
server-1 | OK     | Region "/Trade" created on "server-1"
server-2 | OK     | Region "/Trade" created on "server-2"
server-3 | OK     | Region "/Trade" created on "server-3"

Cluster configuration for group 'cluster' is updated.

9. Executing - list regions

List of regions
---------------
Trade

10. Executing - deploy --jar=server/build/libs/server-0.0.1-SNAPSHOT.jar

 Member  |       Deployed JAR        | Deployed JAR Location
-------- | ------------------------- | ---------------------------------------------------------
server-1 | server-0.0.1-SNAPSHOT.jar | <working-directory>/server-1/server-0.0.1-SNAPSHOT.v1.jar
server-2 | server-0.0.1-SNAPSHOT.jar | <working-directory>/server-2/server-0.0.1-SNAPSHOT.v1.jar
server-3 | server-0.0.1-SNAPSHOT.jar | <working-directory>/server-3/server-0.0.1-SNAPSHOT.v1.jar

11. Executing - list functions

 Member  | Function
-------- | -----------------------------------------
server-1 | LogPartitionedRegionBucketDetailsFunction
server-2 | LogPartitionedRegionBucketDetailsFunction
server-3 | LogPartitionedRegionBucketDetailsFunction

************************* Execution Summary ***********************
Script file: startandconfigure.gfsh

Command-1 : start locator --name=locator
Status    : PASSED

Command-2 : set variable --name=APP_RESULT_VIEWER --value=any
Status    : PASSED

Command-3 : configure pdx --read-serialized=true --auto-serializable-classes=.*
Status    : PASSED

Command-4 : start server --name=server-1 --server-port=0 --eviction-heap-percentage=75 --initial-heap=512m --max-heap=512m --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false
Status    : PASSED

Command-5 : start server --name=server-2 --server-port=0 --eviction-heap-percentage=75 --initial-heap=512m --max-heap=512m --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false
Status    : PASSED

Command-6 : start server --name=server-3 --server-port=0 --eviction-heap-percentage=75 --initial-heap=512m --max-heap=512m --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false
Status    : PASSED

Command-7 : list members
Status    : PASSED

Command-8 : create region --name=Trade --type=PARTITION_REDUNDANT_HEAP_LRU --eviction-action=overflow-to-disk
Status    : PASSED

Command-9 : list regions
Status    : PASSED

Command-10 : deploy --jar=server/build/libs/server-0.0.1-SNAPSHOT.jar
Status     : PASSED

Command-11 : list functions
Status     : PASSED
```
### Load Entries
Sample output from the *runclient.sh* script is:

```
./runclient.sh load-all-region 500000

2020-09-07 10:29:01.160  INFO 49253 --- [           main] example.client.Client                    : Starting Client on ...
...
2020-09-07 10:29:11.973  INFO 49253 --- [           main] example.client.service.TradeService      : Putting 500000 trades of size 1024 bytes
2020-09-07 10:29:18.119  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 1000 trades
2020-09-07 10:29:18.744  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 2000 trades
2020-09-07 10:29:19.188  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 3000 trades
2020-09-07 10:29:19.552  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 4000 trades
2020-09-07 10:29:19.914  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 5000 trades
...
2020-09-07 10:30:36.288  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 496000 trades
2020-09-07 10:30:36.358  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 497000 trades
2020-09-07 10:30:36.433  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 498000 trades
2020-09-07 10:30:36.521  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 499000 trades
2020-09-07 10:30:36.592  INFO 49253 --- [           main] example.client.service.TradeService      : Saved 500000 trades
2020-09-07 10:30:36.592  INFO 49253 --- [           main] example.client.service.TradeService      : Put 500000 trades of size 1024 bytes
```
### Log PartitionedRegion Bucket Details
Sample output from the *runclient.sh* script is:

```
./runclient.sh log-partitioned-region-bucket-details

2020-09-07 10:30:46.595  INFO 49360 --- [           main] example.client.Client                    : Starting Client on ...
...
2020-09-07 10:30:51.621  INFO 49360 --- [           main] example.client.Client                    : Started Client in 5.421 seconds (JVM running for 5.848)
2020-09-07 13:30:51.815  INFO 49360 --- [           main] example.client.Client                    : Logged bucket details for region=Trade; result=[true, true, true]
```
Each server's log file will contain a message like:

```
[info 2020/09/07 10:30:51.802 HST <Function Execution Processor2> tid=0x3a] 
Region /Trade contains the following 37 primary buckets:
  Bucket id=4; entries=4,428; entriesInMemory=3,443; entriesOnDisk=985; bytes=4,823,864; bytesInMemory=3,769,973; bytesOnDisk=1,053,891
  Bucket id=7; entries=4,423; entriesInMemory=3,438; entriesOnDisk=985; bytes=4,818,475; bytesInMemory=3,764,545; bytesOnDisk=1,053,930
  Bucket id=11; entries=4,419; entriesInMemory=3,434; entriesOnDisk=985; bytes=4,814,155; bytesInMemory=3,760,192; bytesOnDisk=1,053,963
  Bucket id=16; entries=4,425; entriesInMemory=3,440; entriesOnDisk=985; bytes=4,820,626; bytesInMemory=3,766,704; bytesOnDisk=1,053,922
  Bucket id=18; entries=4,427; entriesInMemory=3,442; entriesOnDisk=985; bytes=4,822,932; bytesInMemory=3,768,995; bytesOnDisk=1,053,937
  Bucket id=20; entries=4,427; entriesInMemory=3,442; entriesOnDisk=985; bytes=4,822,776; bytesInMemory=3,768,870; bytesOnDisk=1,053,906
  Bucket id=23; entries=4,430; entriesInMemory=3,445; entriesOnDisk=985; bytes=4,826,097; bytesInMemory=3,772,187; bytesOnDisk=1,053,910
  Bucket id=24; entries=4,431; entriesInMemory=3,446; entriesOnDisk=985; bytes=4,827,252; bytesInMemory=3,773,355; bytesOnDisk=1,053,897
  Bucket id=26; entries=4,429; entriesInMemory=3,444; entriesOnDisk=985; bytes=4,825,091; bytesInMemory=3,771,166; bytesOnDisk=1,053,925
  Bucket id=30; entries=4,420; entriesInMemory=3,435; entriesOnDisk=985; bytes=4,815,226; bytesInMemory=3,761,266; bytesOnDisk=1,053,960
  Bucket id=31; entries=4,417; entriesInMemory=3,432; entriesOnDisk=985; bytes=4,811,913; bytesInMemory=3,758,000; bytesOnDisk=1,053,913
  Bucket id=40; entries=4,422; entriesInMemory=3,437; entriesOnDisk=985; bytes=4,817,484; bytesInMemory=3,763,533; bytesOnDisk=1,053,951
  Bucket id=43; entries=4,431; entriesInMemory=3,446; entriesOnDisk=985; bytes=4,827,308; bytesInMemory=3,773,337; bytesOnDisk=1,053,971
  Bucket id=44; entries=4,431; entriesInMemory=3,446; entriesOnDisk=985; bytes=4,827,237; bytesInMemory=3,773,311; bytesOnDisk=1,053,926
  Bucket id=47; entries=4,426; entriesInMemory=3,441; entriesOnDisk=985; bytes=4,821,758; bytesInMemory=3,767,855; bytesOnDisk=1,053,903
  Bucket id=49; entries=4,429; entriesInMemory=3,444; entriesOnDisk=985; bytes=4,824,975; bytesInMemory=3,771,072; bytesOnDisk=1,053,903
  Bucket id=53; entries=4,426; entriesInMemory=3,441; entriesOnDisk=985; bytes=4,821,804; bytesInMemory=3,767,863; bytesOnDisk=1,053,941
  Bucket id=56; entries=4,425; entriesInMemory=3,441; entriesOnDisk=984; bytes=4,820,691; bytesInMemory=3,767,821; bytesOnDisk=1,052,870
  Bucket id=57; entries=4,428; entriesInMemory=3,443; entriesOnDisk=985; bytes=4,823,982; bytesInMemory=3,770,002; bytesOnDisk=1,053,980
  Bucket id=60; entries=4,430; entriesInMemory=3,445; entriesOnDisk=985; bytes=4,826,193; bytesInMemory=3,772,254; bytesOnDisk=1,053,939
  Bucket id=64; entries=4,425; entriesInMemory=3,440; entriesOnDisk=985; bytes=4,820,719; bytesInMemory=3,766,830; bytesOnDisk=1,053,889
  Bucket id=66; entries=4,425; entriesInMemory=3,440; entriesOnDisk=985; bytes=4,820,794; bytesInMemory=3,766,796; bytesOnDisk=1,053,998
  Bucket id=71; entries=4,415; entriesInMemory=3,430; entriesOnDisk=985; bytes=4,809,792; bytesInMemory=3,755,770; bytesOnDisk=1,054,022
  Bucket id=73; entries=4,425; entriesInMemory=3,440; entriesOnDisk=985; bytes=4,820,666; bytesInMemory=3,766,754; bytesOnDisk=1,053,912
  Bucket id=74; entries=4,426; entriesInMemory=3,441; entriesOnDisk=985; bytes=4,821,765; bytesInMemory=3,767,802; bytesOnDisk=1,053,963
  Bucket id=78; entries=4,430; entriesInMemory=3,445; entriesOnDisk=985; bytes=4,826,271; bytesInMemory=3,772,295; bytesOnDisk=1,053,976
  Bucket id=80; entries=4,429; entriesInMemory=3,444; entriesOnDisk=985; bytes=4,825,035; bytesInMemory=3,771,126; bytesOnDisk=1,053,909
  Bucket id=84; entries=4,427; entriesInMemory=3,442; entriesOnDisk=985; bytes=4,822,890; bytesInMemory=3,768,939; bytesOnDisk=1,053,951
  Bucket id=85; entries=4,426; entriesInMemory=3,441; entriesOnDisk=985; bytes=4,821,688; bytesInMemory=3,767,754; bytesOnDisk=1,053,934
  Bucket id=91; entries=4,420; entriesInMemory=3,435; entriesOnDisk=985; bytes=4,815,173; bytesInMemory=3,761,258; bytesOnDisk=1,053,915
  Bucket id=97; entries=4,428; entriesInMemory=3,443; entriesOnDisk=985; bytes=4,824,094; bytesInMemory=3,770,135; bytesOnDisk=1,053,959
  Bucket id=102; entries=4,427; entriesInMemory=3,442; entriesOnDisk=985; bytes=4,822,829; bytesInMemory=3,768,915; bytesOnDisk=1,053,914
  Bucket id=103; entries=4,425; entriesInMemory=3,440; entriesOnDisk=985; bytes=4,820,704; bytesInMemory=3,766,790; bytesOnDisk=1,053,914
  Bucket id=105; entries=4,428; entriesInMemory=3,443; entriesOnDisk=985; bytes=4,823,983; bytesInMemory=3,770,075; bytesOnDisk=1,053,908
  Bucket id=108; entries=4,424; entriesInMemory=3,439; entriesOnDisk=985; bytes=4,819,612; bytesInMemory=3,765,684; bytesOnDisk=1,053,928
  Bucket id=110; entries=4,423; entriesInMemory=3,438; entriesOnDisk=985; bytes=4,818,400; bytesInMemory=3,764,434; bytesOnDisk=1,053,966
  Bucket id=112; entries=4,422; entriesInMemory=3,438; entriesOnDisk=984; bytes=4,817,417; bytesInMemory=3,764,541; bytesOnDisk=1,052,876
  Total entries=163,749; entriesInMemory=127,306; entriesOnDisk=36,443; bytes=178,391,671; bytesInMemory=139,398,199; bytesOnDisk=38,993,472

Region /Trade contains the following 39 redundant buckets:
  Bucket id=1; entries=4,427; entriesInMemory=941; entriesOnDisk=3,486; bytes=4,762,833; bytesInMemory=1,030,382; bytesOnDisk=3,732,451
  Bucket id=2; entries=4,427; entriesInMemory=1,911; entriesOnDisk=2,516; bytes=4,786,094; bytesInMemory=2,092,535; bytesOnDisk=2,693,559
  Bucket id=9; entries=4,424; entriesInMemory=1,793; entriesOnDisk=2,631; bytes=4,780,009; bytesInMemory=1,963,261; bytesOnDisk=2,816,748
  Bucket id=10; entries=4,421; entriesInMemory=1,912; entriesOnDisk=2,509; bytes=4,779,689; bytesInMemory=2,093,639; bytesOnDisk=2,686,050
  Bucket id=14; entries=4,415; entriesInMemory=1,901; entriesOnDisk=2,514; bytes=4,773,115; bytesInMemory=2,081,595; bytesOnDisk=2,691,520
  Bucket id=17; entries=4,425; entriesInMemory=1,765; entriesOnDisk=2,660; bytes=4,780,574; bytesInMemory=1,932,727; bytesOnDisk=2,847,847
  Bucket id=22; entries=4,431; entriesInMemory=1,441; entriesOnDisk=2,990; bytes=4,779,082; bytesInMemory=1,577,830; bytesOnDisk=3,201,252
  Bucket id=27; entries=4,426; entriesInMemory=1,687; entriesOnDisk=2,739; bytes=4,779,640; bytesInMemory=1,847,218; bytesOnDisk=2,932,422
  Bucket id=28; entries=4,426; entriesInMemory=1,813; entriesOnDisk=2,613; bytes=4,782,696; bytesInMemory=1,985,231; bytesOnDisk=2,797,465
  Bucket id=32; entries=4,420; entriesInMemory=1,764; entriesOnDisk=2,656; bytes=4,775,080; bytesInMemory=1,931,509; bytesOnDisk=2,843,571
  Bucket id=36; entries=4,420; entriesInMemory=1,884; entriesOnDisk=2,536; bytes=4,777,977; bytesInMemory=2,062,970; bytesOnDisk=2,715,007
  Bucket id=39; entries=4,419; entriesInMemory=1,289; entriesOnDisk=3,130; bytes=4,762,542; bytesInMemory=1,411,390; bytesOnDisk=3,351,152
  Bucket id=42; entries=4,429; entriesInMemory=1,846; entriesOnDisk=2,583; bytes=4,786,717; bytesInMemory=2,021,354; bytesOnDisk=2,765,363
  Bucket id=45; entries=4,428; entriesInMemory=1,277; entriesOnDisk=3,151; bytes=4,771,999; bytesInMemory=1,398,322; bytesOnDisk=3,373,677
  Bucket id=48; entries=4,426; entriesInMemory=2,003; entriesOnDisk=2,423; bytes=4,787,139; bytesInMemory=2,193,199; bytesOnDisk=2,593,940
  Bucket id=52; entries=4,424; entriesInMemory=1,985; entriesOnDisk=2,439; bytes=4,784,716; bytesInMemory=2,173,550; bytesOnDisk=2,611,166
  Bucket id=55; entries=4,421; entriesInMemory=1,865; entriesOnDisk=2,556; bytes=4,778,601; bytesInMemory=2,042,205; bytesOnDisk=2,736,396
  Bucket id=58; entries=4,427; entriesInMemory=1,945; entriesOnDisk=2,482; bytes=4,786,922; bytesInMemory=2,129,694; bytesOnDisk=2,657,228
  Bucket id=62; entries=4,427; entriesInMemory=1,625; entriesOnDisk=2,802; bytes=4,779,128; bytesInMemory=1,779,288; bytesOnDisk=2,999,840
  Bucket id=67; entries=4,422; entriesInMemory=1,859; entriesOnDisk=2,563; bytes=4,779,459; bytesInMemory=2,035,563; bytesOnDisk=2,743,896
  Bucket id=68; entries=4,419; entriesInMemory=1,408; entriesOnDisk=3,011; bytes=4,765,576; bytesInMemory=1,541,755; bytesOnDisk=3,223,821
  Bucket id=69; entries=4,420; entriesInMemory=1,337; entriesOnDisk=3,083; bytes=4,764,733; bytesInMemory=1,463,959; bytesOnDisk=3,300,774
  Bucket id=70; entries=4,419; entriesInMemory=1,950; entriesOnDisk=2,469; bytes=4,778,551; bytesInMemory=2,135,215; bytesOnDisk=2,643,336
  Bucket id=72; entries=4,418; entriesInMemory=1,654; entriesOnDisk=2,764; bytes=4,770,386; bytesInMemory=1,811,156; bytesOnDisk=2,959,230
  Bucket id=76; entries=4,426; entriesInMemory=1,645; entriesOnDisk=2,781; bytes=4,778,710; bytesInMemory=1,801,283; bytesOnDisk=2,977,427
  Bucket id=77; entries=4,428; entriesInMemory=1,056; entriesOnDisk=3,372; bytes=4,766,758; bytesInMemory=1,156,314; bytesOnDisk=3,610,444
  Bucket id=82; entries=4,429; entriesInMemory=1,423; entriesOnDisk=3,006; bytes=4,776,496; bytesInMemory=1,558,131; bytesOnDisk=3,218,365
  Bucket id=83; entries=4,426; entriesInMemory=1,858; entriesOnDisk=2,568; bytes=4,783,792; bytesInMemory=2,034,495; bytesOnDisk=2,749,297
  Bucket id=86; entries=4,422; entriesInMemory=1,734; entriesOnDisk=2,688; bytes=4,776,510; bytesInMemory=1,898,733; bytesOnDisk=2,877,777
  Bucket id=89; entries=4,419; entriesInMemory=1,089; entriesOnDisk=3,330; bytes=4,757,859; bytesInMemory=1,192,437; bytesOnDisk=3,565,422
  Bucket id=90; entries=4,420; entriesInMemory=1,946; entriesOnDisk=2,474; bytes=4,779,431; bytesInMemory=2,130,827; bytesOnDisk=2,648,604
  Bucket id=92; entries=4,419; entriesInMemory=1,764; entriesOnDisk=2,655; bytes=4,774,067; bytesInMemory=1,931,544; bytesOnDisk=2,842,523
  Bucket id=93; entries=4,420; entriesInMemory=1,764; entriesOnDisk=2,656; bytes=4,775,069; bytesInMemory=1,931,573; bytesOnDisk=2,843,496
  Bucket id=95; entries=4,421; entriesInMemory=1,673; entriesOnDisk=2,748; bytes=4,774,041; bytesInMemory=1,831,981; bytesOnDisk=2,942,060
  Bucket id=99; entries=4,430; entriesInMemory=1,697; entriesOnDisk=2,733; bytes=4,784,231; bytesInMemory=1,858,204; bytesOnDisk=2,926,027
  Bucket id=101; entries=4,434; entriesInMemory=1,585; entriesOnDisk=2,849; bytes=4,785,875; bytesInMemory=1,735,582; bytesOnDisk=3,050,293
  Bucket id=104; entries=4,427; entriesInMemory=1,627; entriesOnDisk=2,800; bytes=4,779,309; bytesInMemory=1,781,574; bytesOnDisk=2,997,735
  Bucket id=107; entries=4,427; entriesInMemory=1,261; entriesOnDisk=3,166; bytes=4,770,489; bytesInMemory=1,380,749; bytesOnDisk=3,389,740
  Bucket id=109; entries=4,425; entriesInMemory=1,995; entriesOnDisk=2,430; bytes=4,786,153; bytesInMemory=2,184,541; bytesOnDisk=2,601,612
  Total entries=172,534; entriesInMemory=64,972; entriesOnDisk=107,562; bytes=186,302,048; bytesInMemory=71,143,515; bytesOnDisk=115,158,533
```
### Shutdown Locator and Servers
Sample output from the *shutdownall.sh* script is:

```
./shutdownall.sh 

(1) Executing - connect

Connecting to Locator at [host=localhost, port=10334] ..
Connecting to Manager at [host=xxx.xxx.x.xx, port=1099] ..
Successfully connected to: [host=xxx.xxx.x.xx, port=1099]


(2) Executing - shutdown --include-locators=true

Shutdown is triggered
```
