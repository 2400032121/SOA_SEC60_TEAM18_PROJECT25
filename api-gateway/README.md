# API Gateway - S025 Auction System

Port: 8080
No database required.

Routes:
- /auth/** -> AUTH-SERVICE
- /auctions/** -> AUCTION-SERVICE
- /bids/** -> BIDDING-SERVICE
- /payments/** -> PAYMENT-SERVICE

Requires Eureka Server on port 8761 and the four services registered in Eureka.
