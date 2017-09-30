package travel.service;

import travel.data.TravelConfirmAnswer;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="TravelApproval"/*, targetNamespace = "http://informatique.polytech.unice.fr/soa1/travel/"*/)
public interface ITravelApprovalService {

    @WebResult(name="travel_approval_result")//TODO link to a sender component for mocking mail
    TravelConfirmAnswer travelConfirm(@WebParam(name="travelConfirmAnswer") TravelConfirmAnswer answer);

}
