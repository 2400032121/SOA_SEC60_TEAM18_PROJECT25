# S025 BidVelocity Bidding Service

Port: 8083
Database: quickbid_bidding

Create database:
CREATE DATABASE quickbid_bidding;

Replace YOUR_POSTGRES_PASSWORD in application.properties.

Start Eureka and Auction Service first.

Endpoints:
POST /bids
GET  /bids/auction/{auctionId}
GET  /bids/{id}

Example POST:
{
  "auctionId": 1,
  "userId": 10,
  "amount": 56000
}

Bidding Service communicates with AUCTION-SERVICE using Eureka + Spring Cloud LoadBalancer.
