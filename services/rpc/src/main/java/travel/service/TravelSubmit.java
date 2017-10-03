package travel.service;

import travel.Storage;
import travel.data.TravelSubmitAnswer;
import travel.data.TravelSubmitRequest;

import javax.jws.WebService;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/travel/",
        portName          = "ExternalTravelSubmitPort",
        serviceName       = "ExternalTravelSubmitService",
        endpointInterface = "travel.service.ITravelSubmitService")
public class TravelSubmit implements ITravelSubmitService {

    @Override
    public TravelSubmitAnswer travelRequest(TravelSubmitRequest request) {
        TravelSubmitAnswer answer = new TravelSubmitAnswer();

        try {
            String uuid = Storage.create(request);
            answer.setUuid(uuid);
            answer.setSuccess(true);
        } catch (Exception e) {
            answer.setSuccess(false);
        }
        return answer;
    }
}
