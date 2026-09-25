package com.bidvelocity.bidding.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class AuctionClient {

    private final RestClient restClient;

    public AuctionClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Map<String, Object> getAuction(Long auctionId) {
        return restClient.get()
                .uri("http://AUCTION-SERVICE/auctions/{id}", auctionId)
                .retrieve()
                .body(Map.class);
    }

    public void updateHighestBid(Long auctionId, BigDecimal amount) {
        restClient.put()
                .uri("http://AUCTION-SERVICE/auctions/{id}/highest-bid?amount={amount}",
                        auctionId, amount)
                .retrieve()
                .toBodilessEntity();
    }
}
