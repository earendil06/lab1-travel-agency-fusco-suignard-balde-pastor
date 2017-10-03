package planner.service;

import planner.data.Storage;

import javax.jws.WebService;

@WebService(targetNamespace   = "",
        portName          = "HotelPlannerPort",
        serviceName       = "HotelPlannerService",
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

    public static void main(String[] args) {
       System.out.println(Storage.findAll().toString());
    }
}
