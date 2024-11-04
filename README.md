## Currency App

The Currency App integrates a third-party currency exchange API to retrieve real-time exchange rates & calculate the total payable amount for a bill in a specified currency after applying applicable discounts. This application exposes an API endpoint that allows users to submit a bill in one currency and get the payable amount in another currency.

### Technology Used

- java11
- apache-maven3.6.3
- spring-boot2.7.3
- jacoco-plugin0.8.5
- docker

### Go to the project root directory and follow the instructions below to build & run the application

- Run the below command to build the application & generate jacoco report (which includes code-coverage details). The Path for the jacoco report is: `reports/jacoco/index.html`
  > mvn clean install -U
- Install or Setup docker on your machine & verify the same using below command
  > docker --version
- Make sure docker-deamon is running-up prior
  - ###### Window:
    > wsl -l -v
  - ###### Linux/Mac:
    > sudo service docker status
- Run the below commands in the terminal to build, start & stop all the services
    - ###### Build:
      > docker build -t currency-app .
    - ###### Run:
      > docker-compose up
    - ###### Stop:
      > docker-compose down

### Please use the below APIs details to test the application once services are up & running
- URL: http://localhost:8081/api/calculate
- HTTP-Method: POST 
- Request Body: {
  "items": [
  {
  "name": "Product 1",
  "price": 50.00,
  "isGrocery": false
  },
  {
  "name": "Product 2",
  "price": 30.00,
  "isGrocery": true
  }
  ],
  "userType": "LOYAL_CUSTOMER",
  "customerTenure": 12,
  "totalAmount": 20.00,
  "originalCurrency": "USD",
  "targetCurrency": "EUR"
  }
- Response Body: {
  "payableAmount": 70.0603720000,
  "targetCurrency": "EUR"
  }

### Security
- Only authenticated users can access /api/calculate.
- To test, you’ll use Basic Authentication with the credentials user:password or admin:adminpass