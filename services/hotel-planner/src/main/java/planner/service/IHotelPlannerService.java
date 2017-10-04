package planner.service;

import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="HotelPlanner", targetNamespace = "")
public interface IHotelPlannerService {

    @WebResult(name="hotel_planner_result")
    String getHotels();

}
