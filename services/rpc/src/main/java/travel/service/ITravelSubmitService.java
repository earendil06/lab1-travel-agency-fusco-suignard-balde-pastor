package travel.service;

import travel.data.TravelSubmitRequest;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="TravelSubmit"/*, targetNamespace = "http://informatique.polytech.unice.fr/soa1/travel/"*/)
public interface ITravelSubmitService {

    @WebResult(name="travel_approval_result")
    TravelSubmitRequest travelRequest(@WebParam(name="travelRequestInfo") TravelSubmitRequest request);
}
