package planner.service;

import planner.data.Hotel;
import planner.data.Storage;

import javax.jws.WebService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebService(targetNamespace = "planner.service",
        portName = "HotelPlannerPort",
        serviceName = "HotelPlannerService",
        endpointInterface = "planner.service.IHotelPlannerService")
public class HotelPlannerService implements IHotelPlannerService {

    @Override
    public String getHotels() {
        try {
            return Storage.findAllHotels().toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    public String getHotelsForTravel(String place, String from, String to) {
        DateFormat format = new SimpleDateFormat(Hotel.DATE_PATTERN);
        try {
            return Storage.getHotelsForTravel(place, format.parse(from), format.parse(to)).toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static void main(String[] args) throws ParseException {
//        DateFormat format = new SimpleDateFormat(Hotel.DATE_PATTERN);
//        Hotel h1 = new Hotel("aa", "Japon", 100, format.parse("10/10/2017"), format.parse("17/10/2017"));
//
//        Storage.initialize();
//        Storage.purge();
//        Storage.createHotel(h1);
//        System.out.println(h1);
//
//        System.out.println(Storage.getHotelsForTravel("Japon", format.parse("09/10/2017"), format.parse("17/10/2017")).toString());
//        System.out.println(Storage.findAllHotels());
    }
}