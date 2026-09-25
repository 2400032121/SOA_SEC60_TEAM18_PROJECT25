# Payment Service - S025 Auction System
Port: 8084
Database: quickbid_payment

Endpoints:
POST /payments
GET /payments
GET /payments/{id}
GET /payments/auction/{auctionId}
PUT /payments/{id}/process

Create body:
{"auctionId":1,"winnerId":15,"amount":53000}

New payment starts PENDING. Process it with PUT /payments/{id}/process to make it SUCCESS.
Update YOUR_POSTGRES_PASSWORD before running.
