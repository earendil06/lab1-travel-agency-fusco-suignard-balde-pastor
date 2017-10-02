package planner.service;

//import planner.data.Storage;

import planner.data.Storage;

import javax.jws.WebService;

@WebService(targetNamespace   = "",
        portName          = "CarPlannerPort",
        serviceName       = "CarPlannerService",
        endpointInterface = "planner.service.ICarPlannerService")
public class CarPlannerService implements ICarPlannerService {
    @Override
    public String getCars() {
        //JSONArray carRentals = Storage.getCarsAtPlaceAndDuration(place, duration);
        try {
            return Storage.findAll().toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    //public static void main(String[] args) {
     //   System.out.println(Storage.findAll().toString());
    //}
}
