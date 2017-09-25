package travelapproval.service;

import travelapproval.data.TravelRequest;
import travelapproval.data.TravelResponse;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="TravelApproval", targetNamespace = "http://informatique.polytech.unice.fr/soa1/travel/")
public interface TravelApprovalService {

    @WebResult(name="travel_approval_result")
    TravelResponse travelRequest(@WebParam(name="travelRequestInfo") TravelRequest request);

}
