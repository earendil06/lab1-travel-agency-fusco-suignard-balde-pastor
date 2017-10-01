package travel.service;

import travel.data.TravelSubmitAnswer;
import travel.data.TravelSubmitRequest;

import javax.jws.WebService;
import java.util.UUID;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",
        portName          = "ExternalTravelComputerPort",
        serviceName       = "ExternalTravelComputerService",
        endpointInterface = "travel.service.ITravelApprovalService")
public class TravelSubmit implements ITravelSubmitService {

    @Override
    public TravelSubmitAnswer travelRequest(TravelSubmitRequest request) {
        TravelSubmitAnswer answer = new TravelSubmitAnswer();

        try {
            //TODO save in database the request

            String uuidRequest = UUID.randomUUID().toString();
            answer.setUuid(uuidRequest);
            answer.setSuccess(true);
        } catch (Exception e) {
            answer.setSuccess(false);
        }


        return answer;
    }
}
