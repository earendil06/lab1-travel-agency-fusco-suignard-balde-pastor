package planner.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="CarPlanner", targetNamespace = "")
public interface ICarPlannerService {

    @WebResult(name="all_car_result")
    String getAllCars();

    @WebResult(name="car_planner_result")
    String getCarByPlace(@WebParam(name="place") String place, @WebParam(name="duration") int duration);

}
