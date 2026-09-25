package com.bidvelocity.payment.controller;

import com.bidvelocity.payment.entity.*;
import com.bidvelocity.payment.repository.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository repo;

    public PaymentController(PaymentRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment p) {
        p.setStatus(PaymentStatus.PENDING);
        p.setPaymentTime(LocalDateTime.now());
        return ResponseEntity.ok(repo.save(p));
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<?> process(@PathVariable Long id) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));

        p.setStatus(PaymentStatus.SUCCESS);
        p.setPaymentTime(LocalDateTime.now());

        Payment saved = repo.save(p);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Payment> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> one(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/auction/{auctionId}")
    public List<Payment> byAuction(@PathVariable Long auctionId) {
        return repo.findByAuctionId(auctionId);
    }
}
