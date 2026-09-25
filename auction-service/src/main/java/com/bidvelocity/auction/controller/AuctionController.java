package com.bidvelocity.auction.controller;

import com.bidvelocity.auction.entity.Auction;
import com.bidvelocity.auction.entity.AuctionStatus;
import com.bidvelocity.auction.repository.AuctionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionRepository repository;

    public AuctionController(AuctionRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Auction> create(@RequestBody Auction auction) {
        auction.setStatus(AuctionStatus.SCHEDULED);
        auction.setCurrentHighestBid(auction.getStartingPrice());
        return ResponseEntity.ok(repository.save(auction));
    }

    @GetMapping
    public List<Auction> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Auction input) {
        return repository.findById(id).map(auction -> {
            if (input.getItemName() != null) auction.setItemName(input.getItemName());
            if (input.getDescription() != null) auction.setDescription(input.getDescription());
            if (input.getStartingPrice() != null) auction.setStartingPrice(input.getStartingPrice());
            if (input.getMinimumBidIncrement() != null) auction.setMinimumBidIncrement(input.getMinimumBidIncrement());
            if (input.getStartTime() != null) auction.setStartTime(input.getStartTime());
            if (input.getEndTime() != null) auction.setEndTime(input.getEndTime());
            if (input.getStatus() != null) auction.setStatus(input.getStatus());
            return ResponseEntity.ok(repository.save(auction));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable Long id) {
        return repository.findById(id).map(auction -> {
            auction.setStatus(AuctionStatus.ACTIVE);
            return ResponseEntity.ok(repository.save(auction));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<?> close(@PathVariable Long id, @RequestParam(required = false) Long winnerId) {
        return repository.findById(id).map(auction -> {
            auction.setStatus(AuctionStatus.CLOSED);
            if (winnerId != null) auction.setWinnerId(winnerId);
            return ResponseEntity.ok(repository.save(auction));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/highest-bid")
    public ResponseEntity<?> updateHighestBid(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {

        return repository.findById(id).map(auction -> {
            if (auction.getStatus() != AuctionStatus.ACTIVE) {
                return ResponseEntity.badRequest().body(Map.of("message", "Auction is not active"));
            }

            BigDecimal minimum = auction.getCurrentHighestBid()
                    .add(auction.getMinimumBidIncrement());

            if (amount.compareTo(minimum) < 0) {
                return ResponseEntity.badRequest().body(
                        Map.of("message", "Bid must be at least " + minimum));
            }

            auction.setCurrentHighestBid(amount);
            return ResponseEntity.ok(repository.save(auction));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
