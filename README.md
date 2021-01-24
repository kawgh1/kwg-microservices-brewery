multi-module maven project containing brewery microservices and servers

##### Part of John Thompson's Microservices course

- **Also need to run Eureka Server** with ("local-discovery") Profile set so that the Failover Service can be found (GET requested by the other Microservices)
    at **http://localhost:8083/inventory-failover**
- Access Eureka Console to verify @ http://localhost:8761/
  

### Default Port Mappings - For Single Host
| Service Name | Port | 
| --------| -----|
| [Brewery Beer Service](https://github.com/kawgh1/mssc-beer-service) | 8080 |
| [Brewery Beer Order Service](https://github.com/kawgh1/mssc-beer-order-service) | 8081 |
| [Brewery Beer Inventory Service](https://github.com/kawgh1/mssc-beer-inventory-service) | 8082 |
| [Inventory Failover Service](https://github.com/kawgh1/mssc-inventory-failover) | 8083 |
| [Netflix Eureka Server](https://github.com/kawgh1/brewery-eureka-server) | 8761
| [Spring Cloud Gateway Server](https://github.com/kawgh1/mssc-brewery-gateway) | 9090
| [Spring Cloud Config Server](https://github.com/kawgh1/mssc-spring-cloud-config-server) | 8888

- ### Spring Cloud Config Client
	- Spring Cloud Config Client by default will look for a URL property
		- 'spring.cloud.config.url' - default is http://localhost:8888