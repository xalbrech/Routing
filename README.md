# Routing 

This is an interview exercise project. 

The application finds a land route between two countries based on list of countries located at : `https://raw.githubusercontent.com/mledoze/countries/master/countries.json`. 
To calculate the route, list of each country borders (neighbourghing countries) from the above URL is used. 

Upon start, the application exposes a single service, `/routing/{origin}/{destination}', that allows to search for a route by country codes (cca3).   
Results are returned as an array of codes of countries between (and including) origin and destination.

If land route between the countries does not exist, or if one of the country codes is not found, the application responds with HTTP status code 400 and a simple JSON object with details of the error.

## Build a WAR file with the application
Using git and maven: 
1. 'git clone https://github.com/xalbrech/Routing.git'
2. 'cd Routing'
3. 'mvn package'
4. Pick the WAR from 'target/' subdirectory.

## Run from command line
On a checked-out project, run maven:
1. 'git clone https://github.com/xalbrech/Routing.git'
2. 'mvn spring-boot:run'
3. Spring Boot starts a web container at local port 8080
4. To test the application, open a new browser window and navigate to: 'http://localhost:8080/routing/CZE/ITA'
5. The service shall return: '{"route":["CZE","AUT","ITA"]}'

