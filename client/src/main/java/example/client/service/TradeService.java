package example.client.service;

import example.client.domain.CusipHelper;
import example.client.domain.Trade;
import example.client.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TradeService {

  @Autowired
  protected TradeRepository tradeRepository;

  protected static final Random random = new Random();

  protected static final Logger logger = LoggerFactory.getLogger(TradeService.class);

  public void put(int numEntries, int entrySize) {
    logger.info("Putting {} trades of size {} bytes", numEntries, entrySize);
    for (int i=0; i<numEntries; i++) {
      Trade trade = new Trade(String.valueOf(i), CusipHelper.getCusip(), random.nextInt(100), new BigDecimal(BigInteger.valueOf(random.nextInt(100000)), 2), new byte[entrySize]);
      this.tradeRepository.save(trade);
      logger.info("Saved " + trade);
    }
    logger.info("Put {} trades of size {} bytes", numEntries, entrySize);
  }

  public void putAll(int numEntries, int entrySize) {
    logger.info("Putting {} trades of size {} bytes", numEntries, entrySize);
    List<Trade> trades = new ArrayList<>();
    for (int i=0; i<numEntries; i++) {
      Trade trade = new Trade(String.valueOf(i), CusipHelper.getCusip(), random.nextInt(100), new BigDecimal(BigInteger.valueOf(random.nextInt(100000)), 2), new byte[entrySize]);
      trades.add(trade);
      if (trades.size() == 1000) {
        this.tradeRepository.saveAll(trades);
        logger.info("Saved " + (i+1) + " trades");
        trades.clear();
      }
    }
    if (!trades.isEmpty()) {
      this.tradeRepository.saveAll(trades);
      logger.info("Saved " + trades.size() + " trades");
    }
    logger.info("Put {} trades of size {} bytes", numEntries, entrySize);
  }
}
