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

CircleCI badge 
   - [![CircleCI](https://circleci.com/gh/kawgh1/mssc-beer-service.svg?style=svg)](https://circleci.com/gh/kawgh1/mssc-beer-service)

# MSSC Beer Service - Microservice

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

# [Contents](#contents)
1. [Java Messaging Service (JMS)](#java-messaging-service-jms)
2. [Data Source(MySQL) Connection Pooling](#data-sourcemysql-connection-pooling)
3. [HikariCP with Spring Boot 2.x](#hikaricp-with-spring-boot-2x)
4. [Ehcache](#ehcache)
5. [Spring MVC REST Docs](#spring-mvc-rest-docs)


### [Java Messaging Service (JMS)](#java-messaging-service-jms)
- What is JMS?
    - JMS is a Java API which allows a Java Application to send a message to another application
        - Generally the other application is a Java application - but not always!
    - JMS is a standard Java API which requires an underlying implementation to be provided
        - For example, JPA - where JPA is the API standard, and Hibernate is the implementation
    - JMS is highly scalable and allows you to loosely couple applications using **asynchronous messaging**
    
- JMS Implementations
    - Amazon SQS
    - Apache ActiveMQ - used in this project
    - JBoss Messaging
    - IBM MQ (closed source / paid)
    - OracleAQ - (closed source / paid)
    - RabbitMQ
    - Many more!
    
- Why use JMS over REST?
    - JMS is a true messaging service
    - Asynchronous - send it and forget it!
    - Greater throughput - the HTTP protocol is slow comparatively
        - JMS protocols are **VERY** performant
    - Flexibility in message delivery - Deliver to one or many consumers
    - Security - JMS has very robust security
    - Reliability - Can guarantee message delivery
    
- Types of Messaging
    - **Point to Point**
        - Message is queued and deliver to one consumer
        - Can have multiple consumers - but message will be delivered only ***ONCE*** (ie to exactly one consumer)
        - Consumers connect to a queue
        
    - **Publish / Subscribe**
        - Message is delivered to one or more subscribers
        - Subscribers will subscribe to a ***topic***, then receive a copy of all messages sent to the topic
        
- Key Terms
    - **JMS Provider** - JMS Implementation
    - **JMS Client** - Application which sends or receives messages from the JMS Provider
    - **JMS Producer (Publisher)** - JMS Client which sends messages
    - **JMS Consumer (Subscriber)** - JMS Client which receives messages
    - **JMS Message** - the entity of data sent (see below)
    - **JMS Queue** - Queue for point to point messages. Often, not always, FIFO
    - **JMS Topic** - Similiar to a queue - but for publish & subscribe
    
[Top](#contents)
    
- ## JMS Message
    - ### A JMS Message contains three parts:
        - ### Header 
            - contains meta data about the message
        - ### Properties 
            - Message properties are in 3 sections:
                - **Application** - From Java Application sending message
                - **Provider** - Used by the JMS Provider and are implementation specific
                - **Standard Properties** - Defined by the JMS API - might not be supported by the Provider
        - ### Payload
            - the message itself
            
    - ### JMS Header Properties
        - #### JMSCorrelationID 
            - String - typically a UUID. Set by application, often used to trace a message through multiple consumers
        - #### JMSExpires 
            - Long
                - zero, does not expire.
                - else, time when message will expire and be removed form the queue.
        - #### JMSMessageID
            - String - typically set by the JMS Provider
        - #### JMSPriority
            - Integer - Priority of the message
        - #### JMSTimestamp
            - Long - Time message was sent
        - #### JMSType
            - String - The type of the message
        - #### JMSReplyTo
            - Queue or topic to which sender is expecting replies
        - #### JMSRedelivery
            - Boolean - Has messaged been re-delivered?
        - #### JMSDeliveryMode
            - Integer
                - set by JMS Provider for Delivery Mode
                    - Persistent (Default) - JMS Provider should make best effort to deliver message
                    - Non-Persistent - Occasional message loss is acceptable
                    
    - ### JMS Message Properties
        - #### JSMX
            - String - User ID sending message. Set by JMS Provider.
        - #### JMSXAppID
            - String - ID of the application sending the message. Set by JMS Provider.
        - #### JMSXDeliveryCount
            - Int - Number of delivery attempts. Set by JMS Provider.
        - #### JMSXGroupID
            - String - The message group which the message if part of. Set by JMS Client.
        - #### JMSXGroupSeq
            - Int - Sequence number of message in group. Set by JMS Client.
        - #### JMSXProducerTDIX
            - String - Transaction ID when message was produced. Set by JMS Producer.
        - #### JSMXConsumerTDIX
            - String - Transaction ID when the message was consumed. Set by JMS Provider.
        - #### JMSXRcvTimestamp
            - Long - Timestamp when messaged delivered to consumer. Set by JMS Provider.
        - #### JMSXState
            - Int - State of the JMS Message. Set by JMS Provider.
                
    - #### JMS Custom Properties
        - typically where work/config about the metadata occurs
        - JMS Client can set custom properties on messages
        - Properties are set as key / value pairs (String, value)
        - Values must be one of:
            - String, boolean, byte, double, float, int, short, long or Object
            
    - #### JMS Provider Properties
        - The JMS Client can also set JMS Provider Specific properties
        - These properties are set as JMS_<provider name>
        - JMS Provider specific properties allow the client to utilize features specific to the JMS Provider
        - Refer to documentation of your selected JMS Provider for details
        
    - ### JMS Message Types
        - #### Message
            - Just a message, no payload. Often used to notify about events.
        - #### BytesMessage
            - Payload is an array of bytes
        - ### TextMessage
            - ***Message is stored as a string (often JSON or XML)***
        - #### StreamMessage
            - sequence of Java primitives
        - #### MapMessage
            - message is name value pairs
        - #### ObjectMessage
            - Message is a serialized Java object
            
    - ### Which Message Type To Use?
        - JMS 1.0 was originally released in 1998 - initial focus was on Java to Java messaging
        - Since 1998, Messaging and technology have grown and evolved beyond the Java ecosystem
        - **JMS TextMessages with JSON or XML payloads are currently favored**
            - Decoupled from Java - can be consumed by any technology
            - Not uncommon to 'bridge' to non-java providers
            - Makes migration to a non-JMS provider less painful
                - Important since messaging is becoming more and more generic and abstracted
                - Fore example, tools like **Spring Integration**
                    - where an application sends a message and as a developer you are completely unaware of what the messaging infrastructure is
            
            
[Top](#contents)
                
          
                
                
                 
             

### [Data Source(MySQL) Connection Pooling](#data-sourcemysql-connection-pooling)
- #### Establishing a Database Connection is an expensive operation
    - Call out to Database Server to get authenticated
    - Database Server needs to authenticate credentials
    - Database Server establishes a connection
    - Database Server establishes a session - ie allocate memory and resources
    
- #### Datasource Optimizations
    - Prepared Statements: SQL Statements with placeholders for variables
        - Saves server from having to parse and optimize execution plan
        - Huge Cost Savings (performance)
        - Avoid SQL Injection attacks (security)
    - Optimizations within a single datasource connection:
        - Ability to cache prepared statements (may be at the server level too)
        - Use serve side prepared statements
        - Statement Batching (series of INSERTs or series of UPDATEs)
        
    - Datasource Connection Pooling
        - In between the Database Server and the Client exists a connection pool of existing, established connections waiting for work to do
        - When a Client Request comes in, it grabs a connection, does its work in the Database Server and then releases the connection
        - Spring Boot 1.x used Tomcat
        - Spring Boot 2.x moved to HikariCP
            - HikariCP is very light weight
            - Very high performance!
            - Hikari has a number of configuration options
            
    - Hacker's Guide to Connection Pool Tuning
        - Every RDMS will accept a max number of connections - each connection has a cost (Server memory, Server CPU, etc.)!
        - If running multiple instances of your microservice (multiple Spring Boot Contexts), keep number of pool connections lower
            - If fewer instances, can go to a higher number of connections per instance
            - Every instance you add, adds 5-10+ pool connections, and each of those connections has to be managed by the BackEnd Server
        - MySQL defaults to a limit of 151 connections
            - Can be adjusted to much higher - depending on the hardware running MySQL
        - Statement caching is good
            - BUT - does consume memory on the server
        - Disabling autocommit can help improve performance
        - **More Connections is ***NOT*** always better!**
        
[Top](#contents)
        
### [HikariCP with Spring Boot 2.x](#hikaricp-with-spring-boot-2x)

- https://github.com/brettwooldridge/HikariCP
- #### Recommended settings:

    - spring.datasource.hikari.maximum-pool-size=5 (relative to # of instances, server capabilities, etc.)

    - spring.datasource.hikari.data-source-properties.cachePrepStmts=true
    - spring.datasource.hikari.data-source-properties.prepStmtCacheSize=250
    - spring.datasource.hikari.data-source-properties.prepStmtCacheSqlLimit=2048
    - spring.datasource.hikari.data-source-properties.useServerPrepStmts=true
    - spring.datasource.hikari.data-source-properties.useLocalSessionState=true
    - spring.datasource.hikari.data-source-properties.rewriteBatchedStatements=true
    - spring.datasource.hikari.data-source-properties.cacheResultSetMetadata=true
    - spring.datasource.hikari.data-source-properties.cacheServerConfiguration=true
    - spring.datasource.hikari.data-source-properties.elideSetAutoCommits=true
    - spring.datasource.hikari.data-source-properties.maintainTimeStats=false
    
    - #### Enable logging for Hikari Connection Pool tuning, config, troubleshooting
        - logging.level.org.hibernate.SQL=DEBUG
        - logging.level.com.zaxxer.hikari.HikariConfig=DEBUG
        - logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
        
[Top](#contents)
    
### [Ehcache](#ehcache)

- https://www.baeldung.com/ehcache
- Ehcache utilizes Java's on-heap RAM memory to store cache entries
- this application will use Ehcache - very popular, robust and one of the top Java caching managers
- will add caching layer for listBeers, getBeerById and getBeerByUpc  - Ehcache is a good candidate because the beer information isn't going to change that much
- ### what it will do?
    - provide fast access to the Beer data while avoiding a call to the database 
    - significantly improves the performance of our getBeer APIs
    - here we set it up to only run when we are NOT getting BeerInventory information (conditional caching)
        - because inventory is dynamic and changes often and quickly
        
- each running instance is going to have its own local cache, so if you have 3 instances running you have 1 in 3 chance of getting a cache response
- there are technologies available where instances can share a cache 
- Ehcache can be configured so that if you do have multiple instances running, it reads from a single cache

[Top](#contents)





### [Spring MVC REST Docs](#spring-mvc-rest-docs)
- #### **What is it?** A tool for generating API documentation from controller tests
- Developed by Andy Wilkinson of Pivotal
- Spring REST Docs hooks into controller tests to generate documentation snippets
- ***Idea being - as soon as your controller tests fail, your API Docs fail also, so there's no delay***
- The snippets are then assembled into final documentation via AsciiDoctor
- Test Clients Supported:
    - Spring MVC Test
    - WebTestClient (WebFlux)
    - REST Assured
    
- Spring REST Docs supports the following testing frameworks:
    - JUnit 5, JUnit 4
    - Spock
    - Test NG (additional configuration required)
    
- Default Snippets
    - curl-request
    - http-request
    - http-response
    - httpie-request
    - request-body
    - response-body
    
- Spring REST Doc Options
    - Can optionally use Markdown rather than AsciiDoctor
    - Maven and Gradle may be used for the build process
    - You can package the documentation to be served as static content via Sping Boot
    - Extensive options for customizing AsciiDoctor output
    
- Third Party Extensions

    - restdocs-wiremock - Auto generate WireMock Stubs
    - restdocsext-jersey - Enable use of REST Docs with Jersey's test framework
    - spring-auto-restdocs - Use reflection to automatically document request and response params
    - restdocs-api-spec - Generate OpenAPI 2 and OpenAPI 3 specifications
    
    


[Top](#contents)

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

