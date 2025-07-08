# 🏦 Midas Core Transaction Processing System

This project is a Kafka-integrated Spring Boot application designed to simulate secure transaction handling between users, reward incentives, and provide a REST API for balance queries.

## ✅ Key Features

- 🔄 Kafka Integration with Embedded Broker (TestContainers)
- 🧾 JPA-based Transaction Recording using H2 DB
- ✅ Business Rule Validation (Balance, Users)
- 🎁 External Incentive API Integration via RestTemplate
- 📊 Exposed REST API for Real-Time Balance Queries
- 🧪 Comprehensive Test Coverage (TaskOne to TaskFiveTests)

## 🔧 Tech Stack
- Java 17, Spring Boot 3.2.5
- Spring Kafka 3.1.4
- H2 Database (2.2.224)
- TestContainers (Kafka 1.19.1)
- Maven

## 📁 Tasks Completed
1. Project Setup and Dependency Management
2. Kafka Listener and Message Deserialization
3. Transaction Validation & DB Persistence
4. Incentive API Integration
5. RESTful Balance Endpoint on Port 33400

> Built as part of the Forage x Midas Systems backend engineering challenge.
