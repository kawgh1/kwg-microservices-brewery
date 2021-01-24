# Spring Cloud Gateway

# For microservices to use JMS Messaging on localhost, Docker must be installed and localhost must be connected to an ActiveMQ Server
[https://github.com/vromero/activemq-artemis-docker](https://github.com/vromero/activemq-artemis-docker)
### defaults for this docker image - github.com/vromero/activemq-artemis
spring.artemis.user=artemis
spring.artemis.password=simetraehcapa

[Docker ActiveMQ](#docker-activemq)

### Running on Server Port 9090

##### based on project by John Thompson (Spring Framework Guru)

[John Thompson lecture](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/learn/lecture/18386668)

- ### API Gateway Pattern
	- #### Gateway Responsibilities
		- Routing / Dynamic Routing
		- Security
		- Rate Limiting
		- Monitoring / Logging
		- Blue / Green Deployments
		- Caching
		- Monolith Strangling

	- #### Types of Gateways
		- Appliances / Hardware - example: F5
		- SAAS (Software as a Service) - AWS Elastic Load Balancer
		- Web Servers - Configured as Proxies
		- Developer Oriented - Zuul (Netflix) or Spring Cloud Gateway
		- Others - not an exhaustive list, technology is evolving & overlapping
		- Types are often combined

- ### Developer Oriented Gateways
	- #### Zuul (Netflix) - Zuul is the 'gatekeeper' in the movie Ghostbusters
	- #### History of Zuul
		- Netflix announced in June 2013 it was opensourcing Zuul
			- "Edge Service in the Cloud"
			- 1,000 different client types
			- 50,000+ requests per second
			- Reminder - at times in the past, during evening when everyone is home, Netflix traffic has been estimated to up to 1/3 of all US network traffic

	- #### Problems with Zuul 1
		- Used Java's HTTP Servlet API
			- Blocking - inefficient
			- Did not support HTTP 2
		- September 2016 Netflix moved to Zuul 2
			- Non-Blocking - much more efficient
			- Support for HTTP 2
			- Announced they planned to open source Zuul 2

- ### Spring Cloud Gateway
    - In 2017, Spring Cloud team decided to not migrate Spring Cloud to Zuul 2
    - Direction of Zuul 2 was unclear at the time
    - While Netflix open sourced Zuul 1, some components were still closed source
    - Also when Spring 5 had recently gone GA (General Availability), which included reactive support
    - First milestone release in August 2017
    - 1.0 GA Release in Novemeber 2017
      
- ### Spring Cloud Gateway Features
    - Java 8+, Spring Framework 5, Spring Boot 2, Project Reactor
    - Non-blocking, HTTP 2 Support, Netty
    - Dynamic Routing
    - Route Mapping on HTTP Request attributes
    - Filters for HTTP Request and Response

- Depending on your architecture, you may not even use a Gateway. You might be deploying on AWS using Elastic Load Balancers, so the Gateway component might not even be in your architecture.
- ### But in a Spring Cloud Context, you are NOT locked into a specific vendor.
    - can deploy Spring Cloud Gateway literally on some generic Linux servers and have full functionality
    - Not tied to Docker, Google Cloud, AWS, Kubernetes, Docker Swarm, Windows, Linux, etc. - totally agnostic
    
    
    
    
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