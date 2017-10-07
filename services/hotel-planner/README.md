#Hotel Planner RPC Service

#**API**

##Hotel Rental Data Type

An HotelRental is a JSONObject with the following data:
- name : String
    - name of the hotel
- place : String
    - localisation of the hotel
- day : int
    - day of the location
- month : int
    - month of the location
- year : int
    - year of the location
- price : int
    -  price of the location

##get all hotels:

 - Entry Point 
	 - http://localhost:9090/service-hotel-planner/HotelPlannerService
- Return a SOAP envelope with all_hotels_result tag containing a JSONArray of HotelRental json object

- Example:
    - http://localhost:9090/service-hotel-planner/HotelPlannerService
    -   Body:
        ```xml
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
            <soap:Body>
                <getHotels xmlns="http://service.planner/">
                </getHotels>
            </soap:Body>
        </soap:Envelope>
        ``` 
    - returns 
              
      ```xml
      <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                        <soap:Body>
                            <ns2:getHotelsResponse xmlns:ns2="http://service.planner/">
                                <all_hotels_result>
                                  [{"uid":"1cc82f93-5dcc-45d5-8ac6-9b821a9a673f","month":5,"year":2017,"price":100,"name":"aa","place":"Japon","day":10}]
                              </all_hotels_result>
                            </ns2:getHotelsResponse>
                        </soap:Body>
                    </soap:Envelope>
      ``` 
    
##Get hotels for a specific travel:

- Entry Point
    - http://localhost:9090/service-hotel-planner/HotelPlannerService
- Return a SOAP envelope with hotel_planner_result tag containing a JSONArray of HotelRental json object

- Example:
    - http://localhost:9090/service-hotel-planner/HotelPlannerService
    -   Body:
        ```xml
        <?xml version="1.0" encoding="utf-8"?>
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
            <soap:Body>
                <getHotelsForTravel xmlns="http://service.planner/">
                	<place xmlns="">Japon</place>
                	<day xmlns="">10</day>
                	<month xmlns="">5</month>
                	<year xmlns="">2017</year>
                </getHotelsForTravel>
            </soap:Body>
        </soap:Envelope>
        ``` 
    - returns 
              
      ```xml
      <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
              <ns2:getHotelsForTravelResponse xmlns:ns2="http://service.planner/">
                  <hotel_planner_result>
                      [{"uid":"1cc82f93-5dcc-45d5-8ac6-9b821a9a673f","month":5,"year":2017,"price":100,"name":"aa","place":"Japon","day":10}]
                  </hotel_planner_result>
              </ns2:getHotelsForTravelResponse>
          </soap:Body>
      </soap:Envelope>
      ``` 