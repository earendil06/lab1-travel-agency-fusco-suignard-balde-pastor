package travel.service;

import travel.data.TravelConfirmAnswer;

import javax.jws.WebService;

@WebService(/*targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",*/
        portName          = "ExternalTravelComputerPort",
        serviceName       = "ExternalTravelComputerService",
        endpointInterface = "travel.service.ITravelApprovalService")
public class TravelApproval implements ITravelApprovalService {

    @Override
    public TravelConfirmAnswer travelConfirm(TravelConfirmAnswer answer) {
        return null;
    }
}
