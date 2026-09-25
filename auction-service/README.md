# S025 BidVelocity Auction Service

Port: 8082
Database: quickbid_auction

Create database:
CREATE DATABASE quickbid_auction;

Replace YOUR_POSTGRES_PASSWORD in application.properties.

Start Eureka first, then run AuctionServiceApplication.java.

Main endpoints:
POST   /auctions
GET    /auctions
GET    /auctions/{id}
PUT    /auctions/{id}
PUT    /auctions/{id}/activate
PUT    /auctions/{id}/close
PUT    /auctions/{id}/highest-bid?amount=56000
DELETE /auctions/{id}
