# Directa24 Back-End Developer Challenge 
## Solution
### Elementos incluidos:
- **Desarrollo de la actividad usando WebFlux**
- **Esquema de Reintentos usando spring-retry** La aplicación es capas de reintentar la invocación del mock de eron-movies en caso de fallo para cada una de las paginas.
- **Pruebas unitarias** Cuenta con pruebas unitarias con cobertura del 80.2%
- **Docker** La aplicación posee Dockerfile para levantar a aplicación dentro de un contenedor
- **Swagger**
- **Actuator** Configuración basica de Actuator.
- **Wiremock** En caso de no tener acceso al Mock de eron-movies, tengo mi propio mock del servicio para uso de manera local en caso de estar fuera de linea.
- **Postman** Colección Postman de la aplicación

### Configuración de Reintentos
En el archivo de application yml se encuentra la configuración del numero de reintentos y el tiempo en milisegundos entre reintentos en caso de fallo para el consumo del mock de eron
```
services:
  retries: 3
  period-retry: 2000
```
### Pruebas unitarias<br><br>
![Cobertura de pruebas unitarias del proyecto.](/UnitTestingCoverage.png)

### Levantar la aplicación en Docker
La aplicación se puede levantar en un contendor docker, para esto es necesario generar el jar con el siguiente comando en la raiz del proyecto:
```
mvn clean install
```
Construir la imagen con el comando a partir del Dockerfile
```
docker build -t movie-directors .
```
Iniciar un contenedor. 
La aplicación posee 2 perfile:
- **dev:** Le apunta al mock de eron
- **local:** Le apunta al mock local de Wiremock
La variable de entorno SPRING_PROFILES_ACTIVE podra ser configurada con cualquiera de estos 2 perfiles dependiendo sus necesidades
```
docker run --name movie-directors -e "SPRING_PROFILES_ACTIVE=dev" -p 8080:8080 --network=host -t movie-directors
```
### Swagger
Para acceder a la consola de Swagger mediante la siguiente url:
```
http://localhost:8080/swagger-ui/index.html
```
<br><br>
![Swagger.](/Swagger.png)
### Wiremock
En caso de ser necesario tener un mock que reemplace eron-movies, se puede levantar un mock local que puede reemplazarlo.
Desde la carpeta raiz del proyecto acceder a la carpeta /WireMock y desde ahí ejecutar el siguiente comando, esto levantara el mock en el puerto 9090:
```
java -jar wiremock-standalone-3.9.1.jar --verbose --port 9090
```
En caso de querer realizar algun ajuste al mock se puede acceder al archivo /WireMock/mappings/eron-pages.json donde encontrara un codigo similar a este en el cual podrá realizar sus respectivos ajustes. Luego debera detener y volver a ejecuar **java -jar wiremock-standalone-3.9.1.jar --verbose --port 9090**
para que tome los cambios.
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
La colección cuenta con 3 request:
- **Java service - Techinical Test:** Request al endpoint del proyecto
- **WireMock - offline:** Request al Mock local
- **Eron Mock:**: Request al Mock de Eron<br><br>
![Colección Postman.](/Postman.png)