package travel.service;

import travel.data.TravelSubmitRequest;

import javax.jws.WebService;

@WebService(/*targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",*/
        portName          = "ExternalTravelComputerPort",
        serviceName       = "ExternalTravelComputerService",
        endpointInterface = "travel.service.ITravelApprovalService")
public class TravelSubmit implements ITravelSubmitService {

    @Override
    public TravelSubmitRequest travelRequest(TravelSubmitRequest request) {
        return null;
    }
}
