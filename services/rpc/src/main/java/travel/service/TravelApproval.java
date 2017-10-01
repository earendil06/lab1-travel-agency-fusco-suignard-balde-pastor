package travel.service;

import travel.data.TravelConfirmAnswer;
import travel.data.TravelSubmitRequest;

import javax.jws.WebService;
import java.util.Arrays;

@WebService(/*targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",*/
        portName          = "ExternalTravelComputerPort",
        serviceName       = "ExternalTravelComputerService",
        endpointInterface = "travel.service.ITravelApprovalService")
public class TravelApproval implements ITravelApprovalService {

    @Override
    public TravelConfirmAnswer travelConfirm(TravelConfirmAnswer answer) {
        TravelConfirmAnswer travelSubmitRequest = new TravelConfirmAnswer();
        travelSubmitRequest.setConfirmed(true);
        travelSubmitRequest.setIdentifier("rrrrrrr");
        return travelSubmitRequest;
    }
}
