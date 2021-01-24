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