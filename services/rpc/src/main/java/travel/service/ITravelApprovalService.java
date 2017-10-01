package travel.service;

import travel.data.TravelConfirmAnswer;
import travel.data.TravelConfirmRequest;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="TravelApproval", targetNamespace = "http://informatique.polytech.unice.fr/soa1/travel/")
public interface ITravelApprovalService {

    @WebResult(name="travel_approval_result")
    TravelConfirmAnswer travelConfirm(@WebParam(name="confirmation") TravelConfirmRequest request);

}
