#Flight Retrieving Document Service

#**API**

##Flight Retrieving Data Type
- Entry Point http://localhost:9080/tcs-service-doc/flights
	 
A Flight retrieving is a JSONObject with the following data:
- event : RETRIEVE (mandatory)

All the others fields are not mandatory

To filtering, use these fields :
- from : String 
    - the  of the flight      
- to : String 
    - the destination of the flight
- date : String
    - the date of the flight (dd/MM/YYYY format)
- hour : String
    - the hour of the flight (HH:mm format)
- duration : integer (in minutes)
    - the duration of the flight
- price : double
    - the price of the flight
- direct : boolean
    - true if the flight is a direct one, false if not

To ordering results use :
- order-by : String (choice between : from, to, duration, price and direct)

To query with min max values, use :
- max : JsonObject with parameters (duration or price)
- min : JsonObject with parameters (duration or price)
    
    
The query returns a Json array.
##Example:

- Example: http://localhost:9080/tcs-service-doc/flights
    -   Body:
        ```json
        {
            "event" : "RETRIEVE",
            "from" : "Paris",
            "to" : "London",
            "order-by" : "price",
            "max" : { "price" : 150 },
            "min" : { "duration" : 100 }
        }
        ``` 