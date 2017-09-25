package travelapproval.service;

import travelapproval.data.TravelRequest;
import travelapproval.data.TravelResponse;

import javax.jws.WebService;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",
        portName          = "ExternalTravelComputerPort",
        serviceName       = "ExternalTravelComputerService",
        endpointInterface = "travelapproval.service.TravelApprovalService")
public class TravelApprovalImpl implements TravelApprovalService {
    @Override
    public TravelResponse travelRequest(TravelRequest request) {
        return null;
    }
}
