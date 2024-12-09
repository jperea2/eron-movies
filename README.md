# Directa24 Back-End Developer Challenge 
## Solution
### Items included:
- **Development of the activity using WebFlux**
- **Retry Scheme using spring-retry** The application is able to retry the invocation of the eron-movies mock in case of failure for each of the pages.
- **Unit tests** It has unit tests with coverage of 88.5%
- **Docker** The application has a Dockerfile to build the application inside a container
- **Swagger**
- **Actuator** Basic configuration of Actuator.
- **Wiremock** In case I don't have access to the eron-movies Mock, I have my own mock of the service to use locally in case I am offline.
- **Postman** Postman App Collection

### Retry Settings
In the application yml file you will find the configuration of the number of retries and the time in milliseconds between retries in case of failure for the consumption of the eron mock
```
services:
  retries: 3
  period-retry: 2000
```
### Unit tests<br><br>
![Project unit test coverage](/UnitTestingCoverage.png)

### Lift the application in Docker
The application can be built in a docker container, for this it is necessary to generate the jar with the following command in the root of the project:
```
mvn clean install
```
Build image with command from Dockerfile
```
docker build -t movie-directors .
```
Start a container. 
The application has 2 profiles:
- **dev:** Endpoint to eron's mock
- **local:** Endpoint to the local Wiremock mock
The SPRING_PROFILES_ACTIVE environment variable could be configured with any of these 2 profiles depending on your needs.
```
docker run --name movie-directors -e "SPRING_PROFILES_ACTIVE=dev" -p 8080:8080 --network=host -t movie-directors
```
### Swagger
To access the Swagger console using the following url:
```
http://localhost:8080/swagger-ui/index.html
```
<br><br>
![Swagger.](/Swagger.png)

### Wiremock
If it's necessary to have a mock that replaces eron-movies, a local mock can be created that can replace it.
From the root folder of the project, access the /WireMock folder and from there execute the following command, this will raise the mock on port 9090:
```
java -jar wiremock-standalone-3.9.1.jar --verbose --port 9090
```
If you want to make any adjustments to the mock, you can access the file /WireMock/mappings/eron-pages.json where you will find a code similar to this one in which you can make your respective adjustments. Then you should stop and run again **java -jar wiremock-standalone-3.9.1.jar --verbose --port 9090**
to make the changes.
```
{
    "mappings": [{
            "request": {
                "method": "GET",
                "urlPathPattern": "/api/movies/search",
                "queryParameters": {
                    "page": {
                        "equalTo": "1"
                    }
                }
            },
            "response": {
                "status": 200,
                "body": "{ \"page\": 1, \"per_page\": 10, \"total\": 26, \"total_pages\": 3, \"data\": [{ \"Title\": \"Midnight in Paris\", \"Year\": \"2011\", \"Rated\": \"PG-13\", \"Released\": \"10 Jun 2011\", \"Runtime\": \"94 min\", \"Genre\": \"Comedy, Fantasy, Romance\", \"Director\": \"Woody Allen\", \"Writer\": \"Woody Allen\", \"Actors\": \"Owen Wilson, Rachel McAdams, Kathy Bates\" }, { \"Title\": \"The Hateful Eight\", \"Year\": \"2015\", \"Rated\": \"R\", \"Released\": \"30 Dec 2015\", \"Runtime\": \"168 min\", \"Genre\": \"Crime, Drama, Mystery\", \"Director\": \"Quentin Tarantino\", \"Writer\": \"Quentin Tarantino\", \"Actors\": \"Samuel L. Jackson, Kurt Russell, Jennifer Jason Leigh\" }, { \"Title\": \"Silence\", \"Year\": \"2016\", \"Rated\": \"R\", \"Released\": \"13 Jan 2017\", \"Runtime\": \"161 min\", \"Genre\": \"Drama, History\", \"Director\": \"Martin Scorsese\", \"Writer\": \"Jay Cocks, Martin Scorsese, ShÃ»saku EndÃ´\", \"Actors\": \"Andrew Garfield, Adam Driver, Liam Neeson\" }, { \"Title\": \"The Skin I Live In\", \"Year\": \"2011\", \"Rated\": \"R\", \"Released\": \"02 Sep 2011\", \"Runtime\": \"120 min\", \"Genre\": \"Drama, Horror, Thriller\", \"Director\": \"Pedro AlmodÃ³var\", \"Writer\": \"Pedro AlmodÃ³var, AgustÃ­n AlmodÃ³var, Thierry Jonquet\", \"Actors\": \"Antonio Banderas, Elena Anaya, Jan Cornet\" }, { \"Title\": \"Pain and Glory\", \"Year\": \"2019\", \"Rated\": \"R\", \"Released\": \"04 Oct 2019\", \"Runtime\": \"113 min\", \"Genre\": \"Drama\", \"Director\": \"Pedro AlmodÃ³var\", \"Writer\": \"Pedro AlmodÃ³var\", \"Actors\": \"Antonio Banderas, Asier Etxeandia, Leonardo Sbaraglia\" }, { \"Title\": \"The Last Airbender\", \"Year\": \"2010\", \"Rated\": \"PG\", \"Released\": \"01 Jul 2010\", \"Runtime\": \"103 min\", \"Genre\": \"Action, Adventure, Family\", \"Director\": \"M. Night Shyamalan\", \"Writer\": \"M. Night Shyamalan\", \"Actors\": \"Noah Ringer, Nicola Peltz, Jackson Rathbone\" }, { \"Title\": \"Blue Jasmine\", \"Year\": \"2013\", \"Rated\": \"PG-13\", \"Released\": \"23 Aug 2013\", \"Runtime\": \"98 min\", \"Genre\": \"Comedy, Drama\", \"Director\": \"Woody Allen\", \"Writer\": \"Woody Allen\", \"Actors\": \"Cate Blanchett, Alec Baldwin, Peter Sarsgaard\" }, { \"Title\": \"J. Edgar\", \"Year\": \"2011\", \"Rated\": \"R\", \"Released\": \"11 Nov 2011\", \"Runtime\": \"137 min\", \"Genre\": \"Biography, Drama, Romance\", \"Director\": \"Clint Eastwood\", \"Writer\": \"Dustin Lance Black\", \"Actors\": \"Leonardo DiCaprio, Armie Hammer, Naomi Watts\" }, { \"Title\": \"Midnight in Paris\", \"Year\": \"2011\", \"Rated\": \"PG-13\", \"Released\": \"10 Jun 2011\", \"Runtime\": \"94 min\", \"Genre\": \"Comedy, Fantasy, Romance\", \"Director\": \"Woody Allen\", \"Writer\": \"Woody Allen\", \"Actors\": \"Owen Wilson, Rachel McAdams, Kathy Bates\" }, { \"Title\": \"After Earth\", \"Year\": \"2013\", \"Rated\": \"PG-13\", \"Released\": \"31 May 2013\", \"Runtime\": \"100 min\", \"Genre\": \"Action, Adventure, Sci-Fi\", \"Director\": \"M. Night Shyamalan\", \"Writer\": \"Gary Whitta, M. Night Shyamalan, Will Smith\", \"Actors\": \"Jaden Smith, David Denman, Will Smith\" }, { \"Title\": \"Hugo\", \"Year\": \"2011\", \"Rated\": \"PG\", \"Released\": \"23 Nov 2011\", \"Runtime\": \"126 min\", \"Genre\": \"Adventure, Drama, Family\", \"Director\": \"Martin Scorsese\", \"Writer\": \"John Logan, Brian Selznick\", \"Actors\": \"Asa Butterfield, ChloÃ« Grace Moretz, Christopher Lee\" } ]}",
                "headers": {
                    "Content-Type": "application/json"
                }
            }
        },
```
### Postman
The collection has 3 requests:
- **Java service - Technical Test:** Request to the project endpoint
- **WireMock - offline:** Request to local Mock
- **Eron Mock:**: Request for Eron Mock<br><br>
![Colección Postman.](/Postman.png)