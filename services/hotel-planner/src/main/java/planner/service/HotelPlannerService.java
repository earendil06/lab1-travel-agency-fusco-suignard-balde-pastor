package planner.service;

import planner.data.Storage;

import javax.jws.WebService;

@WebService(targetNamespace = "planner.service",
        portName = "HotelPlannerPort",
        serviceName = "HotelPlannerService",
        endpointInterface = "planner.service.IHotelPlannerService")
public class HotelPlannerService implements IHotelPlannerService {

    @Override
    public String getHotels() {
        try {
            return Storage.findAll().toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String getHotelsForTravel(String place, int day, int month, int year) {
        try {
            return Storage.getHotelsForTravel(place, day, month, year).toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(Storage.findAll().toString());
    }
}
