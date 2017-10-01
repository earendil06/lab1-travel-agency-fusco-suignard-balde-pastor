package travel.service;

import travel.data.TravelSubmitAnswer;
import travel.data.TravelSubmitRequest;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="TravelSubmit", targetNamespace = "http://informatique.polytech.unice.fr/soa1/travel/")
public interface ITravelSubmitService {

    @WebResult(name="travel_submit_result")
    TravelSubmitAnswer travelRequest(@WebParam(name="demand") TravelSubmitRequest request);
}
