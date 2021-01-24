### API Gateways
- #### What's happening in the background
    - Behind the Gateway we may or may not have **Load Balancers**
	- But we're going to have multiple nodes of microservices being created and destroyed and the Gateway needs to know about those somehow.
	- Netflix Eureka is one service that solves this problem

- #### Netflix Eureka
	- Netflix Eureka is a Microservice Discovery and Registration service
		- When a microservice instance starts, it registers itself with the Eureka service
			- Provides host name / IP, port and microservice name
			- **Eureka will be tracking the microservice by its microservice name**, not hostname/IP or port
			- Known as **"Service Registration"**
		- ***Service Discovery*** is the process of discovering the available microservice instances

    - Order of Operations
		1. Microservice node first makes call to register on Eureka service (before anything else, even bootstrap functions)
		2. Then the API Gateway will be calling Eureka ("discovering") for instances it needs based on Client requests it receives
		3. Once the microservice instance is "discovered", the API Gateway can start routing Client traffic to it
		4. A behind the scenes software load balancer called **Ribbon** may be present to balance traffic to microservice nodes behind the Gateway API

- ### Microservice Registration

	- Spring Boot provides a Spring Boot Starter for a Eureka Server
	- Spring Boot also provides a starter for Eureka Clients(microservices)
		- Both will self configure for localhost operations
	- Import to configure microservice name in application.properties
		- This is the value used to lookup the microservice in Eureka

- ### Microservice Discovery
	- **Spring Cloud Open Feign** allows for easy service discovery **between** microservices
		- Works in connection with Eureka and Ribbon
	- Spring Cloud Gateway can be configured to lookup services in Eureka
		- Works in conjunction with Ribbon to load balance requests