package com.bidvelocity.bidding.controller;

import com.bidvelocity.bidding.client.AuctionClient;
import com.bidvelocity.bidding.dto.BidRequest;
import com.bidvelocity.bidding.entity.Bid;
import com.bidvelocity.bidding.repository.BidRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bids")
public class BidController {

    private final BidRepository repository;
    private final AuctionClient auctionClient;

    public BidController(BidRepository repository, AuctionClient auctionClient) {
        this.repository = repository;
        this.auctionClient = auctionClient;
    }

    @PostMapping
    public ResponseEntity<?> placeBid(@RequestBody BidRequest request) {
        try {
            Map<String, Object> auction = auctionClient.getAuction(request.getAuctionId());

            String status = String.valueOf(auction.get("status"));
            if (!"ACTIVE".equals(status)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Auction is not active"));
            }

            Bid bid = new Bid();
            bid.setAuctionId(request.getAuctionId());
            bid.setUserId(request.getUserId());
            bid.setAmount(request.getAmount());
            bid.setBidTime(LocalDateTime.now());

            Bid saved = repository.save(bid);
            auctionClient.updateHighestBid(request.getAuctionId(), request.getAmount());

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Bid rejected: " + e.getMessage()));
        }
    }

    @GetMapping("/auction/{auctionId}")
    public List<Bid> getAuctionBids(@PathVariable Long auctionId) {
        return repository.findByAuctionIdOrderByAmountDesc(auctionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBid(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
