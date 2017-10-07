#Travel Planner REST Service

#**API**

##Travel Request Data Type

A TravelRequest is a JSONObject with the following data:
- uuidRequest : String
    - unique id of the request
- email : String
    - email of the requester
- hotels : array of String
    - array of ids of hotels
- flights : array of String
    - array of ids of flights  


##Create a new Travel Request:

 - Entry Point 
	 - http://localhost:9070/service-travel-manager/TravelPlannerService/request
 - Method **POST**
- Parameters:
    - email of the requester
    - array of hotel ids
    - array of flight ids
- Return the unique id of the generated request

- Example:
    - POST http://localhost:9070/service-travel-manager/TravelPlannerService/request
    -   Body:
        ```json
        {
            "hotels": [
                "id hotel 1",
                "id hotel 2"
            ],
            "flights": [
                "id vol 1"
            ],
            "email": "user@email.com"
        }
        ``` 
    - returns 62b0566f-3d7a-430a-ad34-a66e6ee0baec
    
##Get all pending Travel Requests:

- Entry Point
    - http://localhost:9070/service-travel-manager/TravelPlannerService/request
- Method **GET**
- Return a JSONArray of TravelRequests
- Example:
    - GET http://localhost:9070/service-travel-manager/TravelPlannerService/request
    - returns 
        ```json
        [
            {
                "hotels": [
                    "id hotel 1",
                    "id hotel 2"
                ],
                "flights": [
                    "id vol 1"
                ],
                "email": "user@email.com"
            }
        ]
        ``` 

##Get all pending Requests by email

- Entry Point
    - http://localhost:9070/service-travel-manager/TravelPlannerService/request/email/{email}
- Method **GET**
- Parameters:
    - email : email of the requests desired
- Return a JSONArray of TravelRequests
- Example:
    - GET http://localhost:9070/service-travel-manager/TravelPlannerService/request/email/toto@tutu.com
    
##Get all pending Requests by uuidRequest

- Entry Point
    - http://localhost:9070/service-travel-manager/TravelPlannerService/request/uid/{uuidRequest}
- Method **GET**
- Parameters:
    - email : email of the requests desired
- Return a JSONArray of TravelRequests
- Example:
    - GET http://localhost:9070/service-travel-manager/TravelPlannerService/request/uid/62b0566f-3d7a-430a-ad34-a66e6ee0baec
    
##Validate a request:

 - Entry Point 
	 - http://localhost:9070/service-travel-manager/TravelAcceptationService/validatedRequest/uid/{uuidRequest}
 - Method **POST**
- Parameters:
    - uid of the request to validate
- Returns a acceptance message and send an email to the owner of the request

- Example:
    - POST http://localhost:9070/service-travel-manager/TravelAcceptationService/validatedRequest/uid/1f701709-3ab3-4f8b-9fe4-574f8e86eecd
    - returns "Request 1f701709-3ab3-4f8b-9fe4-574f8e86eecd has been validated"
    
##Refuse a request:

 - Entry Point 
	 - http://localhost:9070/service-travel-manager/TravelAcceptationService/refusedRequest/uid/{uuidRequest}
 - Method **POST**
- Parameters:
    - uid of the request to refuse
- Returns a message and send an email to the owner of the request

- Example:
    - POST http://localhost:9070/service-travel-manager/TravelAcceptationService/refusedRequest/uid/1f701709-3ab3-4f8b-9fe4-574f8e86eecd
    - returns "Request 1f701709-3ab3-4f8b-9fe4-574f8e86eecd has been refused"
    
##Get all validated Requests by email

- Entry Point
    - http://localhost:9070/service-travel-manager/TravelAcceptationService/validatedRequest/email/{email}
- Method **GET**
- Parameters:
    - email : email of the requests desired
- Return a JSONArray of TravelRequests
- Example:
    - GET http://localhost:9070/service-travel-manager/TravelAcceptationService/validatedRequest/email/toto@tutu.com

##Get all refused Requests by email

- Entry Point
    - http://localhost:9070/service-travel-manager/TravelAcceptationService/refusedRequest/email/{email}
- Method **GET**
- Parameters:
    - email : email of the requests desired
- Return a JSONArray of TravelRequests
- Example:
    - GET http://localhost:9070/service-travel-manager/TravelAcceptationService/refusedRequest/email/toto@tutu.com
    