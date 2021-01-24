
##### Part of John Thompson's Microservices course

- **Note: Do NOT have WebFlux and Spring MVC both on the same classpath - unpredictable result will occur**

- **Also need to run Eureka Server** with ("local-discovery") Profile set so that the Failover Service can be found (GET requested by the other Microservices)
    at **http://localhost:8083/inventory-failover**
- Access Eureka Console to verify @ http://localhost:8761/
  
  
## MSSC Inventory Failover Service - Microservice

- The idea with this Inventory Failover Service is if our main Beer Inventory Service fails or is unavailable, this Failover service
    will kick in and at least provide inventory values for new Beer Inventory Requests. It may not be able to fulfill any Order Requests,
    but can at least prevent some downstream errors of failed inventory requests

[![CircleCI](https://circleci.com/gh/kawgh1/mssc-inventory-failover.svg?style=svg)](https://circleci.com/gh/kawgh1/mssc-inventory-failover)

### Default Port Mappings - For Single Host
| Service Name | Port | 
| --------| -----|
| [Brewery Beer Service](https://github.com/kawgh1/mssc-beer-service) | 8080 |
| [Brewery Beer Order Service](https://github.com/kawgh1/mssc-beer-order-service) | 8081 |
| [Brewery Beer Inventory Service](https://github.com/kawgh1/mssc-beer-inventory-service) | 8082 |
| [Inventory Failover Service](https://github.com/kawgh1/mssc-inventory-failover) | 8083 |

- ### Circuit Breaker Pattern

    - The Circuit Breaker Pattern is a simple concept which allows you to recover from errors
    - If a mission critical service is unavailable or has unrecoverable errors, via the Circuit Breaker Pattern you can specify an alternative action
    - For example, we wish to always have inventory for potential orders
        - If the inventory service is down, we can provide a fallback service to respond with inventory

- ### Spring Cloud Circuit Breaker
    - Spring Cloud Circuit Breaker is a project which provides abstractions across several circuit breaker implementations
        - Thus your source code is not tied t a specific implementation (like SLF4J)
    - **Supported:**
        - Netflix Hystrix
        - Resilience4J
        - Sentinel
        - Spring Retry
        
- ### Spring Cloud Gateway Circuit Breakers
    - Spring Cloud Gateway supports Netflix Hystrix and Resilience4J
    - **Gateway Filters** are used on top of the Spring Cloud Circuit Breaker APIs
    - Netflix has placed Hystrix into maintenance mode, Spring suggests to use Resilience4J
    
- ### In this project:
    - #### Create Inventory Failover Service
    - Configure [Spring Cloud Gateway](https://github.com/kawgh1/mssc-brewery-gateway) to use circuit breaker for failover
    - Configure [Beer Inventory Service Feign](https://github.com/kawgh1/mssc-beer-inventory-service/tree/initial-project/src/main/java/com/kwgdev/beer/inventory/service/config) to use Circuit Breaker

### Steps for Deconstruction into  Microservices
##### 1. Dependency Management
##### 2. (Local) MySQL Configuration
##### 3. JMS Messaging
##### 4. JMS with Microservices
##### 5. Spring State Machine
##### 6. Using Sagas with Spring
##### 7. Integration Testing Sagas
##### 8. Compensating Transactions
##### 9. Spring Cloud Gateway
##### 10. Service Registration
##### 11. Service Discovery
##### 12. Circuit Breaker
##### 13. Spring Cloud Config
##### 14. Distributed Tracing

##### For microservices to use JMS Messaging on localhost, Docker must be installed and localhost must be connected to an ActiveMQ Server
[https://github.com/vromero/activemq-artemis-docker](https://github.com/vromero/activemq-artemis-docker)
#### defaults for this docker image - github.com/vromero/activemq-artemis
spring.artemis.user=artemis
spring.artemis.password=simetraehcapa
  
  
[Docker ActiveMQ](#docker-activemq)


**Beer Service** is responsible for generating the Beer objects used in the application and stores that Beer object data in a database. 
**Beer Order Service** and **Beer Inventory** make calls to **Beer Service** to get information about the Beer objects.

Beer object example:

- UUID **id** = '026cc3c8-3a0c-4083-a05b-e908048c1b08' 
- String **beer_name** = 'Pinball Porter' 
- String **beer_style** = 'PORTER' 
- Timestamp **created_date** = CURRENT_TIMESTAMP 
- Timestamp **last_modified_date** = CURRENT_TIMESTAMP 
- Integer **min_on_hand** = 12 
- Integer **quantity_to_brew** = 200 
- BigDecimal **price** = 12.95 
- String **upc** = '0083783375213' 
- Long **version** = 1

