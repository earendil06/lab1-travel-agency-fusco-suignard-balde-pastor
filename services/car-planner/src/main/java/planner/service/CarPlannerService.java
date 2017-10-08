package planner.service;

import planner.data.Storage;

import javax.jws.WebService;

@WebService(targetNamespace = "planner.service",
        portName = "CarPlannerPort",
        serviceName = "CarPlannerService",
        endpointInterface = "planner.service.ICarPlannerService")
public class CarPlannerService implements ICarPlannerService {

    @Override
    public String getAllCars() {
        try {
            return Storage.findAll().toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String getCarByPlace(String place, int duration) {
        try {
            return Storage.getCarsForTravel(place, duration).toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(Storage.findAll().toString());
        System.out.println(Storage.getCarsForTravel("Paris", 24).toString());
    }
}
