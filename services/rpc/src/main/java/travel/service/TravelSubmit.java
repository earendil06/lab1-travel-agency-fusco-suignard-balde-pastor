package travel.service;

import travel.data.TravelSubmitRequest;

import javax.jws.WebService;
import java.util.Arrays;

@WebService(/*targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",*/
        portName          = "ExternalTravelComputerPort",
        serviceName       = "ExternalTravelComputerService",
        endpointInterface = "travel.service.ITravelApprovalService")
public class TravelSubmit implements ITravelSubmitService {

    @Override
    public TravelSubmitRequest travelRequest(TravelSubmitRequest request) {
        TravelSubmitRequest travelSubmitRequest = new TravelSubmitRequest();
        travelSubmitRequest.setIdentifier("adfezdfaz");
        travelSubmitRequest.setFlights(Arrays.asList("ephfi", "phnefe"));
        travelSubmitRequest.setHotels(Arrays.asList("ezfzf", "rberbh"));
        return travelSubmitRequest;
    }
}
