# S025 BidVelocity Auth Service

Port: 8081
Database: quickbid_auth

Before running:
1. Create PostgreSQL database: quickbid_auth
2. Open application.properties
3. Replace YOUR_POSTGRES_PASSWORD with your PostgreSQL password
4. Start Eureka on port 8761
5. Run AuthServiceApplication.java

Endpoints:
POST http://localhost:8081/auth/register
POST http://localhost:8081/auth/login
