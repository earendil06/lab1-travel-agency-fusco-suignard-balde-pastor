package planner.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Date;

@WebService(name="HotelPlanner", targetNamespace = "")
public interface IHotelPlannerService {

    @WebResult(name="all_hotels_result")
    String getHotels();

    @WebResult(name="hotel_planner_result")
    String getHotelsForTravel(@WebParam(name="place") String place,
                              @WebParam(name="from") String from,
                              @WebParam(name = "to") String to
                              );

}
