#Car Planner RPC Service

#**API**

##Car Rental Data Type

A CarRental is a JSONObject with the following data:
- name : String
    - name of the car seller
- place : String
    - localisation of the car
- day : int
    - day of the location
- month : int
    - month of the location
- year : int
    - year of the location
    
##Get all cars:

 - Entry Point 
	 - http://localhost:9060/service-car-planner/CarPlannerService
- Return a SOAP envelope with all_car_result tag containing a JSONArray of CarRental json object

- Example:
    - http://localhost:9060/service-car-planner/CarPlannerService
    -   Body:
        ```xml
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
            <soap:Body>
                <getAllCars xmlns="http://service.planner/">
                </getAllCars>
            </soap:Body>
        </soap:Envelope>
        ``` 
    - returns 
              
      ```xml
      <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
              <ns2:getAllCarsResponse xmlns:ns2="http://service.planner/">
                  <all_car_result>
                      [{"duration":4,"uid":"8f133769-3805-4742-9b7d-05bcee3a0722","name":"peugeot","place":"Paris"}]
                  </all_car_result>
              </ns2:getAllCarsResponse>
          </soap:Body>
      </soap:Envelope>
      ``` 
    
##Get cars for a specific travel:

- Entry Point
    - http://localhost:9060/service-car-planner/CarPlannerService
- Return a SOAP envelope with car_planner_result tag containing a JSONArray of CarRental json object

- Example:
    - http://localhost:9060/service-car-planner/CarPlannerService
    -   Body:
        ```xml
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
            <soap:Body>
                <getCarByPlace xmlns="http://service.planner/">
                	<place xmlns="">Paris</place>
                	<duration xmlns="">2</duration>
                </getCarByPlace>
            </soap:Body>
        </soap:Envelope>
        ``` 
    - returns 
              
      ```xml
      <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
              <ns2:getCarByPlaceResponse xmlns:ns2="http://service.planner/">
                  <car_planner_result>
                      [{"duration":4,"uid":"8f133769-3805-4742-9b7d-05bcee3a0722","name":"peugeot","place":"Paris"}]
                  </car_planner_result>
              </ns2:getCarByPlaceResponse>
          </soap:Body>
      </soap:Envelope>
      ``` 