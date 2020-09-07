package example.client;

import example.client.domain.Trade;
import example.client.function.OnRegionFunctions;
import example.client.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.geode.boot.autoconfigure.ContinuousQueryAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = ContinuousQueryAutoConfiguration.class) // disable subscriptions
@EnableEntityDefinedRegions(basePackageClasses = Trade.class)
public class Client {

  @Autowired
  private TradeService service;

  @Autowired
  protected OnRegionFunctions onRegionFunctions;

  protected static final Logger logger = LoggerFactory.getLogger(Client.class);

  public static void main(String[] args) {
    new SpringApplicationBuilder(Client.class)
      .build()
      .run(args);
  }

  @Bean
  ApplicationRunner runner() {
    return args -> {
      List<String> operations = args.getOptionValues("operation");
      String operation = operations.get(0);
      String parameter1 = (args.containsOption("parameter1")) ? args.getOptionValues("parameter1").get(0) : null;
      switch (operation) {
        case "load-region":
          this.service.put(Integer.parseInt(parameter1), 1024);
          break;
        case "load-all-region":
          this.service.putAll(Integer.parseInt(parameter1), 1024);
          break;
        case "log-partitioned-region-bucket-details":
          Object result = this.onRegionFunctions.logPartitionedRegionBucketDetails();
          logger.info("Logged bucket details for region={}; result={}", "Trade", result);
          break;
    }};
  }
}
