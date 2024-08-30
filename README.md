# dockerLoanBook
 -This application is loanBook app based on microservices each microservice is developed with springboot and it is running on a docker container we have a total of 4 microservices :

 .AuthService for authentication (I used simple authentication)
 .BookService for handeling the books
 .Reservation for handeling the user's reservations

 -Each microservice has its scripted tests
 -I used nginx as an api gateway reverse proxy in order to not expose the ports of my microservices
 -I made a docker-compose to run my application
 
 To run the application via docker-compose you need to do these commands down below in this order :

  (of course you need to pull the necessary images first maven:latest and mariadb:latest if you do not have them)

 1. docker-compose up mariadb-test mariadb --build -d (to start the database of production and the database for the tests)

 2. docker-compose up maven-builder --build -d (to start the maven engine in order to build the dependencies and do the tests of each microservice check the logs to see if there are any existing errors)

 3. docker-compose up authservice frontendservice reservationservice bookservice nginx --build -d (to start the rest of the services)

 (i did --build option because i used to update my code a lot)
 (of course you need to pull the necessary images first maven:latest and mariadb:latest)
 
 run the application on mozilla firefox because css is only being loaded there , in case it doesn't load just clear the browser data and try again
 
 
 The purpose of this application is cia so my code is far from perfect just focus on its schema
