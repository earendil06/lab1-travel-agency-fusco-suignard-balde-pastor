package planner.service;

import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="CarPlanner", targetNamespace = "")
public interface ICarPlannerService {

    @WebResult(name="car_planner_result")
    String getCars();

}
