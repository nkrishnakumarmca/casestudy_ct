## About
  The objective of this application is to read the transaction details from the given input file and validate the transaction as per the  business needs and highlight the failed transactions.

## Configuration Details
  Application works on the files given in the specified physical path location in config.properties file
  It will read the given data in the csv/xml file and validate it and send the failed validation data in response as JSON
  
  `accepted.file.formats=csv,xml`
  `file.path.unprocessed={processed.file.location}`
  `file.path.processed={unprocessed.file.location}`

## Steps to run

1. Checkout the code using the below command 
    ```
    git clone git@github.com:nkrishnakumarmca/casestudy_ct.git
    ```
2. Move into the application folder and run the below command
If you are using Maven
    ```
    mvn clean install
    mvn spring-boot:run
    ```
3. Open browser and hit the below end point

   
    Scenario 1:
    URL : [http://localhost:8080/validateData/csv](http://localhost:8080/validateData/csv)
    
    Response : 
    ```json
    {
    "failedResponse": [],
    "errorMsg": "Files not found in the given location..."
    }
    ```
    Scenario 2:
    URL : [http://localhost:8080/validateData/csd](http://localhost:8080/validateData/csd)
    
    Response :
    ```json
    {
    "failedResponse": null,
    "errorMsg": "Please provide the accepted file formats :: csv,xml"
    }
    ```
    Scenario 3:
    URL : [http://localhost:8080/validateData/csv](http://localhost:8080/validateData/csv)
    
    Response :
    ```json
    {
    "failedResponse": [
    {
    "transactionReference": 112806,
    "description": "Clothes for Richard de Vries"
    },
    {
    "transactionReference": 112806,
    "description": "Tickets from Richard Bakker"
    }
    ],
    "errorMsg": null
    }
    ```
    
