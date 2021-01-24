# Cloud Config Repo

### Default Port Mappings - For Single Host (Cloud)
| Project ||
| --------| -----|
| **[KWG MICROSERVICES BREWERY](https://github.com/kawgh1/kwg-microservices-brewery)** ||
| **Service Name** | **Port** | 
| | |
| **[Brewery Beer Service](https://github.com/kawgh1/mssc-beer-service)** | 8080 |
| **[Brewery Beer Order Service](https://github.com/kawgh1/mssc-beer-order-service)** | 8081 |
| **[Brewery Beer Inventory Service](https://github.com/kawgh1/mssc-beer-inventory-service)** | 8082 |
| **[Inventory Failover Service](https://github.com/kawgh1/mssc-inventory-failover)** | 8083 |
| **[Netflix Eureka Server](https://github.com/kawgh1/brewery-eureka-server)** | 8761
| **[Spring Cloud Gateway Server](https://github.com/kawgh1/mssc-brewery-gateway)** | 9090
| **[Spring Cloud Config Server](https://github.com/kawgh1/mssc-spring-cloud-config-server)** | 8888
| | |
| | |
| | |
| **[Brewery Cloud Config Repo](https://github.com/kawgh1/mssc-brewery-cloud-config-repo)** |  -----|

- ### Server Side Application Configuration
    - Setting up a single application using Spring Cloud Configuration
        - Create directories for each of the Spring Boot Microservices
        - Add configurations for active profiles
        - **Goal** - setup application with profile configuration and test in Postman
        
    - Spring Cloud Config RESTful Endpoints
        - .Configuration Endpoints available endpoints:
            - **/{application}/{profile}[/{label}]**
            - /{appliucation}-{profile}.yml
            - /{label}/{application}-{profile}.yml
            - /{application}-{profile}.properties
            -/{label}/{application}-{profile}.properties
            
        - if you do /{application} you will get 404 Not Found
        - if you do /{application}/default you will get properties
        - John notes he has never had a use for labels in configuration
            - Suggests, if you need it, question *why* you need it