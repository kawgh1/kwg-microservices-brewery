
##### Part of John Thompson's Microservices course

# For microservices to use JMS Messaging on localhost, Docker must be installed and localhost must be connected to an ActiveMQ Server
[https://github.com/vromero/activemq-artemis-docker](https://github.com/vromero/activemq-artemis-docker)
### defaults for this docker image - github.com/vromero/activemq-artemis
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

# Default Port Mappings - For Single Host
| Service Name | Port | 
| --------| -----|
| [Brewery Beer Service](https://github.com/kawgh1/mssc-beer-service) | 8080 |
| [Brewery Beer Order Service](https://github.com/kawgh1/mssc-beer-order-service) | 8081 |
| [Brewery Beer Inventory Service](https://github.com/kawgh1/mssc-beer-inventory-service) | 8082 |
| [Inventory Failover Service](https://github.com/kawgh1/mssc-inventory-failover) | 8083 |

[![CircleCI](https://circleci.com/gh/kawgh1/mssc-beer-inventory-service.svg?style=svg)](https://circleci.com/gh/kawgh1/mssc-beer-inventory-service)
# MSSC Beer Inventory Service - Microservice

## Steps for Deconstruction into  Microservices
#### 1. Dependency Management
#### 2. (Local) MySQL Configuration
#### 3. JMS Messaging
#### 4. JMS with Microservices
#### 5. Spring State Machine
#### 6. Using Sagas with Spring
#### 7. Integration Testing Sagas
#### 8. Compensating Transactions
#### 9. Spring Cloud Gateway
#### 10. Service Registration
#### 11. Service Discovery
#### 12. Circuit Breaker
#### 13. Spring Cloud Config
#### 14. Distributed Tracing

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
    - Create Inventory Failover Service
    - Configure Spring Cloud Gateway to use circuit breaker for failover
    - Configure Feign to use Circuit Breaker

[Docker ActiveMQ](#docker-activemq)

5. Running the image

There are different methods to run a Docker image, from interactive Docker to Kubernetes and Docker Compose. This documentation will cover only Docker with an interactive terminal mode. You should refer to the appropriate documentation for more information around other execution methods.

To run ActiveMQ with AMQP, JMS and the web console open (if your are running 2.3.0 or later), run the following command:

MAC

docker run -it --rm \
  -p 8161:8161 \
  -p 61616:61616 \
  vromero/activemq-artemis
  
  WINDOWS
  docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis

After a few seconds you'll see in the output a block similar to:
  
_        _               _
/ \  ____| |_  ___ __  __(_) _____  
/ _ \|  _ \ __|/ _ \  \/  | |/  __/  
/ ___ \ | \/ |_/  __/ |\/| | |\___ \  
/_/   \_\|   \__\____|_|  |_|_|/___ /  
Apache ActiveMQ Artemis x.x.x  

HH:mm:ss,SSS INFO  [...] AMQ101000: Starting ActiveMQ Artemis Server

At this point you can open the web server port at 8161 and check the web console using the default username and password of artemis / simetraehcapa.