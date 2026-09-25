package com.bidvelocity.bidding.dto;

import java.math.BigDecimal;

public class BidRequest {
    private Long auctionId;
    private Long userId;
    private BigDecimal amount;

    public Long getAuctionId() { return auctionId; }
    public void setAuctionId(Long auctionId) { this.auctionId = auctionId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
